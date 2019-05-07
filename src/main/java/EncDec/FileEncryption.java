package EncDec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for file encryption.
 */
public class FileEncryption {

    /**
     * <code>Logger</code> instance for logging.
     */
    private static Logger logger = LogManager.getLogger(FileEncryption.class);

    //TODO Handle exceptions in a better way

    /**
     * Encrypts a file using <code>PBKDF2WithHmacSHA1</code> and <code>AES</code>.
     *
     * @param inputPath path to the file being encrypted
     * @param password  password for the encryption
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidParameterSpecException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static void encryptFile(String inputPath, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, BadPaddingException, IllegalBlockSizeException {

        // file to be encrypted
        FileInputStream inFileStream = getInputFileStream(inputPath);
//        System.out.print("Enter the file path: ");
//        inputPath = inStream.nextLine();
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

        // encrypted file
        FileOutputStream outFileStream = getOutputFileStream(inputPath);
//        try {
//            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
//            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
//            String outputPath = inputPath.substring(0, lastIndex + 1);
//            outFileStream = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".enc");
//        }
//        catch (Exception e) {
//            logger.error("Incorrect file path. Exception: " + e);
//            return;
//        }

        // password to encrypt the file
//        System.out.print("Enter password: ");
//        String password = inStream.nextLine();

        // password, iv and salt should be transferred to the other end
        // in a secure manner

        // salt is used for encoding
        // writing it to a file
        // salt should be transferred to the recipient securely
        // for decryption
        byte[] salt = getSalt();
//        byte[] salt = new byte[8];
//        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.nextBytes(salt);
//        FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
//        saltOutFile.write(salt);
//        saltOutFile.close();

//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
//        SecretKey secretKey = factory.generateSecret(keySpec);
//        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        SecretKey secret = getSecretKey(password, salt);

        //
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);

//        AlgorithmParameters params = cipher.getParameters();
        // iv adds randomness to the text and just makes the mechanism more
        // secure
        // used while initializing the cipher
        // file to store the iv
//        FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
//        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
//        ivOutFile.write(iv);
//        ivOutFile.close();
        writeIV(cipher);

        //file encryption
        writeEncrypted(inFileStream, outFileStream, cipher);
//        byte[] input = new byte[64];
//        int bytesRead;
//
//        while ((bytesRead = inFileStream.read(input)) != -1) {
//            byte[] output = cipher.update(input, 0, bytesRead);
//            if (output != null)
//                outFileStream.write(output);
//        }
//
//        byte[] output = cipher.doFinal();
//        if (output != null)
//            outFileStream.write(output);
//
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
        } catch (FileNotFoundException e) {
            logger.error("File note found.");
            return null;
        } catch (Exception e) {
            logger.error("File couldn't be located. Exception: " + e);
            return null;
        }
    }

    /**
     * Provides FileOutputStream for the given path.
     *
     * @param inputPath the given path
     * @return the <code>FileOutputStream</code> instance
     */
    private static FileOutputStream getOutputFileStream(String inputPath) {
        try {
            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
            String outputPath = inputPath.substring(0, lastIndex + 1);
            return new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".enc");
        } catch (Exception e) {
            logger.error("Incorrect file path. Exception: " + e);
            return null;
        }
    }

    /**
     * Gives a random salt of <code>byte arrays</code>.
     *
     * @return the salt
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static byte[] getSalt() throws FileNotFoundException, IOException {
        byte[] salt = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
        saltOutFile.write(salt);
        saltOutFile.close();
        return salt;
    }

    /**
     * Provides a secret key with <code>AES</code>.
     *
     * @param password password for the secret key
     * @param salt     salt for the secret key
     * @return the secret key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey getSecretKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    /**
     * Writes out the IV.
     *
     * @param cipher cipher for the IV
     * @throws IOException
     * @throws InvalidParameterSpecException
     */
    private static void writeIV(Cipher cipher) throws IOException, InvalidParameterSpecException {
        AlgorithmParameters params = cipher.getParameters();
        FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        ivOutFile.write(iv);
        ivOutFile.close();
    }

    /**
     * Writes out the encrypted file.
     *
     * @param inFileStream  the input file
     * @param outFileStream the output file
     * @param cipher        the cipher
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static void writeEncrypted(FileInputStream inFileStream, FileOutputStream outFileStream, Cipher cipher) throws IOException, BadPaddingException, IllegalBlockSizeException {
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFileStream.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFileStream.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            outFileStream.write(output);

        inFileStream.close();
        outFileStream.flush();
        outFileStream.close();

        logger.info("File Encrypted.");
    }

    public static void main(String[] args) throws Exception {

        Scanner inStream = new Scanner(System.in);

        // file to be encrypted
        FileInputStream inFile;
        System.out.print("Enter the file path: ");
        String inputPath = inStream.nextLine();
        try {
            inFile = new FileInputStream(inputPath);
        } catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        } catch (Exception e) {
            System.out.println("File couldn't be located. Exception: " + e);
            return;
        }

        // encrypted file
        FileOutputStream outFile;
        try {
            List<Integer> lastIndexOfSeparator = Arrays.asList(inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
            String outputPath = inputPath.substring(0, lastIndex + 1);
            outFile = new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".enc");
        } catch (Exception e) {
            System.out.println("Incorrect file path. Exception: " + e);
            return;
        }

        // password to encrypt the file
        System.out.print("Enter password: ");
        String password = inStream.nextLine();

        // password, iv and salt should be transferred to the other end
        // in a secure manner

        // salt is used for encoding
        // writing it to a file
        // salt should be transferred to the recipient securely
        // for decryption
        byte[] salt = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
        saltOutFile.write(salt);
        saltOutFile.close();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();

        // iv adds randomness to the text and just makes the mechanism more
        // secure
        // used while initializing the cipher
        // file to store the iv
        FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        ivOutFile.write(iv);
        ivOutFile.close();

        //file encryption
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            outFile.write(output);

        inFile.close();
        outFile.flush();
        outFile.close();

        logger.info("File Encrypted.");

    }

}
