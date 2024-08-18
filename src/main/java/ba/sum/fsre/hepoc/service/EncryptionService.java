package ba.sum.fsre.hepoc.service;

import com.n1analytics.paillier.EncryptedNumber;
import com.n1analytics.paillier.PaillierContext;
import com.n1analytics.paillier.PaillierPrivateKey;
import com.n1analytics.paillier.PaillierPublicKey;
import jakarta.annotation.PostConstruct;

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

    public EncryptedNumber encrypt(long value) {
        return context.encrypt(value);
    }

    public long decrypt(EncryptedNumber encryptedNumber) {
        return privateKey.decrypt(encryptedNumber).decodeLong();
    }
}
