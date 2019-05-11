package EncDec;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Class for file decryption.
 */
public class FileDecryption {

    /**
     * <code>Logger</code> instance for logging.
     */
    private static Logger logger = LogManager.getLogger(FileEncryption.class);


    //TODO Handle exceptions in a better way

    /**
     * Decrypts a file using <code>PBKDF2WithHmacSHA1</code> and <code>AES</code>.
     *
     * @param inputPath path to the file being decrypted
     * @param password  password for the decryption
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static void decryptFile(String inputPath, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {

        FileInputStream inFileStream = getInputFileStream(inputPath);
//        System.out.print("Enter the file path: ");
//        String inputPath = inStream.nextLine();

//        try {
//            inFileStream = new FileInputStream(inputPath);
//        }
//        catch (FileNotFoundException e) {
//            logger.error("File note found.");
//            return;
//        }
//        catch (Exception e) {
//            logger.error("File couldn't be located. Exception: " + e);
//            return;
//        }

//        System.out.print("Enter password: ");
//        String password = inStream.nextLine();

        // reading the salt
        // user should have secure mechanism to transfer the
        // salt, iv and password to the recipient
        byte[] salt = readSalt();
//        FileInputStream saltFis = new FileInputStream("salt.enc");
//        byte[] salt = new byte[8];
//        saltFis.read(salt);
//        saltFis.close();

        // reading the iv
        byte[] iv = readIV();
//        FileInputStream ivFis = new FileInputStream("iv.enc");
//        byte[] iv = new byte[16];
//        ivFis.read(iv);
//        ivFis.close();

//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
//        SecretKey tmp = factory.generateSecret(keySpec);
//        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        SecretKey secret = getSecretKey(password, salt);

        // file decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

        FileOutputStream outFileStream = getOutputFileStream(inputPath);
//        try {
//            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
//            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
//            String outputPath = inputPath.substring(0, lastIndex + 1);
//            if (inputPath.lastIndexOf('.') < 0)
//                outFileStream = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".dec");
//            else
//                outFileStream = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1, inputPath.lastIndexOf('.')));
//        }
//        catch (Exception e) {
//            logger.error("Incorrect file path. Exception: " + e);
//            return;
//        }

        writeDecrypted(inFileStream, outFileStream, cipher);
//        byte[] in = new byte[64];
//        int read;
//        while ((read = inFileStream.read(in)) != -1) {
//            byte[] output = cipher.update(in, 0, read);
//            if (output != null)
//                outFileStream.write(output);
//        }
//
//        byte[] output;
//        try {
//            output = cipher.doFinal();
//        } catch (BadPaddingException e) {
//            logger.error("Incorrect password.");
//            return;
//        } catch (Exception e) {
//            logger.error("Unable to decrypt the file, exception: " + e);
//            return;
//        }
//
//        if (output != null)
//            outFileStream.write(output);
//        inFileStream.close();
//        outFileStream.flush();
//        outFileStream.close();

    }

    /**
     * Provides FileInputStream for the given path.
     *
     * @param inputPath the given path
     * @return the <code>FileInputStream</code> object
     */
    private static FileInputStream getInputFileStream(String inputPath) {
        try {
            return new FileInputStream(inputPath);
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.error("File note found.");
            try {
                FileEncryption.showPopupWindow("File not found.");
            } catch (Exception e) {
                logger.debug("Unable to display popup windows. Exception: " + e);
            }
            return null;
        } catch (Exception e) {
            logger.error("File couldn't be located. Exception: " + e);
            return null;
        }
    }

    /**
     * Reads the salt for decryption.
     *
     * @return the salt
     * @throws IOException
     */
    private static byte[] readSalt() throws IOException {
        FileInputStream saltFis = new FileInputStream("salt.enc");
        byte[] salt = new byte[8];
        saltFis.read(salt);
        saltFis.close();
        return salt;
    }

    /**
     * Reads the IV for decryption.
     *
     * @return the IV
     * @throws IOException
     */
    private static byte[] readIV() throws IOException {
        FileInputStream ivFis = new FileInputStream("iv.enc");
        byte[] iv = new byte[16];
        ivFis.read(iv);
        ivFis.close();
        return iv;
    }

    /**
     * Provides a secret key with <code>AES</code>.
     *
     * @param password password for the secret key
     * @param salt     salt for the secret key
     * @return the secret key
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey getSecretKey(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(keySpec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    /**
     * Provides FileOutputStream for the given path.
     *
     * @param inputPath the given path
     * @return the <code>FileOutputStream</code> instance
     */
    private static FileOutputStream getOutputFileStream(String inputPath) {
        FileOutputStream outFileStream;
        try {
            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
            String outputPath = inputPath.substring(0, lastIndex + 1);
            if (inputPath.lastIndexOf('.') < 0)
                outFileStream = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".dec");
            else
                outFileStream = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1, inputPath.lastIndexOf('.')));
            return outFileStream;
        } catch (Exception e) {
            logger.error("Incorrect file path. Exception: " + e);
            return null;
        }
    }

    /**
     * Writes out the decrypted file.
     *
     * @param inFileStream  the input file
     * @param outFileStream the output file
     * @param cipher        the cipher
     * @throws IOException
     */
    private static void writeDecrypted(FileInputStream inFileStream, FileOutputStream outFileStream, Cipher cipher)
            throws IOException {
        byte[] in = new byte[64];
        int read;
        while ((read = inFileStream.read(in)) != -1) {
            byte[] output = cipher.update(in, 0, read);
            if (output != null)
                outFileStream.write(output);
        }

        byte[] output;
        try {
            output = cipher.doFinal();
        } catch (BadPaddingException badPaddingEx) {
            logger.error("Incorrect password.");
            try {
                FileEncryption.showPopupWindow("Incorrect password.");
            } catch (Exception e) {
                logger.debug("Unable to display popup windows. Exception: " + e);
            }
            return;
        } catch (Exception e) {
            logger.error("Unable to decrypt the file, exception: " + e);
            return;
        }

        if (output != null)
            outFileStream.write(output);
        inFileStream.close();
        outFileStream.flush();
        outFileStream.close();

        logger.info("File Decrypted.");
        try {
            FileEncryption.showPopupWindow("File Decrypted.");
        } catch (Exception e) {
            logger.debug("Unable to display popup windows. Exception: " + e);
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner inStream = new Scanner(System.in);

        FileInputStream fis;
        System.out.print("Enter the file path: ");
        String inputPath = inStream.nextLine();
        try {
            fis = new FileInputStream(inputPath);
        } catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        } catch (Exception e) {
            System.out.println("File couldn't be located. Exception: " + e);
            return;
        }

        System.out.print("Enter password: ");
        String password = inStream.nextLine();

        // reading the salt
        // user should have secure mechanism to transfer the
        // salt, iv and password to the recipient
        FileInputStream saltFis = new FileInputStream("salt.enc");
        byte[] salt = new byte[8];
        saltFis.read(salt);
        saltFis.close();

        // reading the iv
        FileInputStream ivFis = new FileInputStream("iv.enc");
        byte[] iv = new byte[16];
        ivFis.read(iv);
        ivFis.close();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        // file decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

        FileOutputStream fos;
        try {
            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
            String outputPath = inputPath.substring(0, lastIndex + 1);
            if (inputPath.lastIndexOf('.') < 0)
                fos = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".dec");
            else
                fos = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1, inputPath.lastIndexOf('.')));
        } catch (Exception e) {
            System.out.println("Incorrect file path. Exception: " + e);
            return;
        }

        byte[] in = new byte[64];
        int read;
        while ((read = fis.read(in)) != -1) {
            byte[] output = cipher.update(in, 0, read);
            if (output != null)
                fos.write(output);
        }

        byte[] output;
        try {
            output = cipher.doFinal();
        } catch (BadPaddingException e) {
            System.out.println("Incorrect password.");
            return;
        } catch (Exception e) {
            System.out.println("Unable to decrypt the file, exception: " + e);
            return;
        }

        if (output != null)
            fos.write(output);
        fis.close();
        fos.flush();
        fos.close();
        System.out.println("File Decrypted.");
    }
}