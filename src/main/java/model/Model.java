package model;

public class Model {

    public void encrypt(String encPath, String encPass) {
        try {
            EncDec.FileEncryption.encryptFile(encPath, encPass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("encryptFile exception");
        }

    }

    public void decrypt(String decPath, String decPass) {
        try {
            EncDec.FileDecryption.decryptFile(decPath, decPass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("decryptFile exception");
        }
    }

    public String generate() {
        return RSA.RSA.getRandomKeys();
    }
}