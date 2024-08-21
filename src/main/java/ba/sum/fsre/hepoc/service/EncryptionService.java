package ba.sum.fsre.hepoc.service;

import com.n1analytics.paillier.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Base64;

@Service
public class EncryptionService {
    private PaillierPrivateKey privateKey;
    private PaillierPublicKey publicKey;
    private PaillierContext context;

    @PostConstruct
    public void init() {
        // Generate a private key and public key pair
        this.privateKey = PaillierPrivateKey.create(2048);
        this.publicKey = privateKey.getPublicKey();

        // Create a Paillier context
        this.context = publicKey.createSignedContext();
    }

    public PaillierPublicKey getPublicKey() {
        return publicKey;
    }

    public PaillierContext getContext() {
        return context;
    }

    public PaillierPrivateKey getPrivateKey() {
        return privateKey;
    }

    public EncryptedNumber encrypt(long d) {
        EncodedNumber encoded = context.encode(d);
        BigInteger value = encoded.getValue();
        BigInteger ciphertext = publicKey.raw_encrypt_without_obfuscation(value);
        return new EncryptedNumber(context, ciphertext, encoded.getExponent());
    }

    public String encryptNumberToString(long number) {
        EncodedNumber encoded = context.encode(number);
        BigInteger value = encoded.getValue();
        BigInteger ciphertext = publicKey.raw_encrypt_without_obfuscation(value);

        EncryptedNumber encryptedNumber = new EncryptedNumber(context, ciphertext, encoded.getExponent());

        // Convert components to string
        String ciphertextStr = ciphertext.toString(16); // Hexadecimal string
        String exponentStr = Integer.toString(encoded.getExponent());

        // Combine them with a delimiter
        String combined = ciphertextStr + ":" + exponentStr;

        // Encode combined string to Base64
        return Base64.getEncoder().encodeToString(combined.getBytes());
    }

    public String encryptedNumberToString(EncryptedNumber encryptedNumber) {
        // Extract the components
        BigInteger ciphertext = encryptedNumber.calculateCiphertext();
        int exponent = encryptedNumber.getExponent();

        // Convert components to string
        String ciphertextStr = ciphertext.toString(16); // Hexadecimal string
        String exponentStr = Integer.toString(exponent);

        // Combine them with a delimiter
        String combined = ciphertextStr + ":" + exponentStr;

        // Encode combined string to Base64
        return Base64.getEncoder().encodeToString(combined.getBytes());
    }


    public long decrypt(EncryptedNumber encryptedNumber) {
        return privateKey.decrypt(encryptedNumber).decodeLong();
    }

    public EncryptedNumber encryptedTextToEncryptedNumber(String encodedString) {
        String decodedString = new String(Base64.getDecoder().decode(encodedString));
        // Split the components
        String[] parts = decodedString.split(":");
        BigInteger ciphertext = new BigInteger(parts[0], 16);
        int exponent = Integer.parseInt(parts[1]);

        return new EncryptedNumber(context, ciphertext, exponent);
    }
}
