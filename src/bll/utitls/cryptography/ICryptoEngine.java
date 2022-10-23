package bll.utitls.cryptography;

public interface ICryptoEngine {

    String Hash(String text);

    Boolean HashCheck(String hash, String text);
}
