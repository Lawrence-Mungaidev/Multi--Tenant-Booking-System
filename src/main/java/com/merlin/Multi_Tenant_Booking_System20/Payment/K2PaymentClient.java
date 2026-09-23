package com.merlin.Multi_Tenant_Booking_System20.Payment;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.util.Map;

@Service
public class K2PaymentClient {

    private final WebClient webClient;

    @Value("${k2.base-url}")
    private String baseUrl;

    public K2PaymentClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String initiateStkPush(String token, String tillNumber, String firstName,
                                  String lastName, String phoneNumber, String email,
                                  String amount, String callbackUrl, Map<String, String> metadata) {

        Map<String, Object> requestBody = Map.of(
                "payment_channel", "M-PESA STK Push",
                "till_number", tillNumber,
                "first_name", firstName,
                "last_name", lastName,
                "phone_number", phoneNumber,
                "email", email,
                "currency", "KES",
                "amount", amount,
                "callback_url", callbackUrl,
                "metadata", metadata
        );

        try {
            URI location = webClient.post()
                    .uri(baseUrl + "/api/v2/incoming_payments")
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .toBodilessEntity()
                    .block()
                    .getHeaders()
                    .getLocation();

            return location != null ? location.toString() : null;

        } catch (WebClientResponseException e) {
            throw new BusinessRuleException("KopoKopo rejected the payment request: " + e.getResponseBodyAsString());
        }
    }
}
