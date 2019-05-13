package model;

import encdec.FileEncryption;
import controller.Main;
import model.DAO.Operation;
import model.DAO.OperationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

/**
 * Represents the model of the <code>MVC</code> application.
 */
public class Model {

    /**
     * <code>Logger</code> instance for logging.
     */
    private static Logger logger = LogManager.getLogger(FileEncryption.class);

    private OperationDAO dao;

    public Model() {
        dao = new OperationDAO();
    }

    /**
     * Encrypts the file from the view and saves the operation in the database.
     *
     * @param encPath path to the file to be encrypted
     * @param encPass password to the file to be encrypted
     */
    public void encrypt(String encPath, String encPass) {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Encryption", encPath, new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        try {
            encdec.FileEncryption.encryptFile(encPath, encPass);

            logger.info("File Encrypted.");
            Main.showPopupWindow("File Encrypted.");
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.error("File not found.");
            Main.showPopupWindow("File not found.");
        } catch (Exception e) {
            logger.error("encryptFile exception");
        }
    }

    /**
     * decrypts the file from the view and saves the operation in the database.
     *
     * @param decPath path to the file to be decrypted
     * @param decPass password to the file to be decrypted
     */
    public void decrypt(String decPath, String decPass) {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Decryption", decPath, new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        try {
            encdec.FileDecryption.decryptFile(decPath, decPass);

            logger.info("File Decrypted.");
            Main.showPopupWindow("File Decrypted.");
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.error("File not found.");
            Main.showPopupWindow("File not found.");
        } catch (Exception e) {
            logger.error("decryptFile exception");
        }
    }

    /**
     * Generates RSA keys and saves the operation on the database.
     *
     * @return the RSA keys in <code>string</code>
     */
    public String generate() {

        java.util.Date today = new java.util.Date();
        Operation operation = new Operation("Key generation", "-", new java.sql.Timestamp(today.getTime()));

        dao.saveOperation(operation);

        return rsa.RSA.getRandomKeys();
    }
}