package blockchain;

import clients.Client;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {

    private static final AtomicInteger counter = new AtomicInteger(1);
    @Getter
    private String info;
    private String signatureString;
    private int id;


    public String sendMoney(int amount, Client client) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        info = "Total amount of " + amount + " VC sent to " + client.toString();
        signatureString = sign();
        client.receiveMoney(amount);
        return signatureString;
    }

    public KeyPair getKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.genKeyPair();
    }

    public String sign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(getKeys().getPrivate());
        privateSignature.update(getInfo().getBytes(StandardCharsets.UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
}
