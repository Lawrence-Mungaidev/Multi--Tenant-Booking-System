package com.merlin.Multi_Tenant_Booking_System20.Payment;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CredentialEncryptionService {

    private final AES256TextEncryptor encryptor;

    public CredentialEncryptionService(@Value("${app.encryption.secret-key}") String secretKey) {
        this.encryptor = new AES256TextEncryptor();
        this.encryptor.setPassword(secretKey);
    }

    public String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
