package model;

import model.DAO.Operation;
import model.DAO.OperationDAO;

import java.util.Calendar;
import java.util.Date;

public class Model {

    private OperationDAO dao;

    public Model() {
        dao = new OperationDAO();
    }

    public void encrypt(String encPath, String encPass) {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Encryption", encPath, new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        try {
            EncDec.FileEncryption.encryptFile(encPath, encPass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("encryptFile exception");
        }
    }

    public void decrypt(String decPath, String decPass) {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Decryption", decPath, new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        try {
            EncDec.FileDecryption.decryptFile(decPath, decPass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("decryptFile exception");
        }
    }

    public String generate() {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Key generation", "-", new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        return RSA.RSA.getRandomKeys();
    }
}