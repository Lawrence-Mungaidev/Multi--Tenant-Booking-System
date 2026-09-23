package com.merlin.Multi_Tenant_Booking_System20.Payment;


import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.VendorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class K2AuthService {

    private final WebClient webClient;

    @Value("${k2.base-url}")
    private String baseUrl;

    @Value("${k2.platform-client-id}")
    private String platformClientId;

    @Value("${k2.platform-client-secret}")
    private String platformClientSecret;

    private final Map<Long, CachedToken> tokenCache = new ConcurrentHashMap<>();
    private final VendorRepository vendorRepository;
    private final CredentialEncryptionService encryptionService;
    private CachedToken platformTokenCache;

    public K2AuthService(VendorRepository vendorRepository,
                         CredentialEncryptionService encryptionService,
                         WebClient.Builder webClientBuilder) {
        this.vendorRepository = vendorRepository;
        this.encryptionService = encryptionService;
        this.webClient = webClientBuilder.build();
    }

    public String getTokenForVendor(Long vendorId) {
        CachedToken cached = tokenCache.get(vendorId);
        if (cached != null && cached.expiresAt().isAfter(Instant.now().plusSeconds(300))) {
            return cached.accessToken();
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFound("Vendor not found"));

        String clientId = encryptionService.decrypt(vendor.getK2ClientId());
        String clientSecret = encryptionService.decrypt(vendor.getK2ClientSecret());

        K2TokenResponse response = requestTokenFromK2(clientId, clientSecret); // <-- no vendorId param anymore

        CachedToken newToken = new CachedToken(
                response.getAccessToken(),
                Instant.now().plusSeconds(response.getExpiresIn())
        );
        tokenCache.put(vendorId, newToken); // caching happens HERE now, not inside the shared method
        return newToken.accessToken();
    }

    private K2TokenResponse requestTokenFromK2(String clientId, String clientSecret) {
        String body = "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";

        K2TokenResponse response = webClient.post()
                .uri(baseUrl + "/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "MultiTenantBookingSystem/1.0 (Java)")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(K2TokenResponse.class)
                .block();

        if (response == null) {
            throw new IllegalStateException("No response from K2 Connect token endpoint");
        }

        return response;
    }

    public String getPlatformToken() {
        if (platformTokenCache != null &&
                platformTokenCache.expiresAt().isAfter(Instant.now().plusSeconds(300))) {
            return platformTokenCache.accessToken();
        }

        K2TokenResponse response = requestTokenFromK2(platformClientId, platformClientSecret);
        platformTokenCache = new CachedToken(
                response.getAccessToken(),
                Instant.now().plusSeconds(response.getExpiresIn())
        );
        return platformTokenCache.accessToken();
    }


    private record CachedToken(String accessToken, Instant expiresAt) {}


    public boolean verifyCredentials(String clientId, String clientSecret) {
        try {
            String body = "client_id=" + clientId
                    + "&client_secret=" + clientSecret
                    + "&grant_type=client_credentials";

            webClient.post()
                    .uri(baseUrl + "/oauth/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "MultiTenantBookingSystem/1.0 (Java)")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(K2TokenResponse.class)
                    .block();

            return true;

        } catch (WebClientResponseException e) {
            return false;
        }
    }
}
