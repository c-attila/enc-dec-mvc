package EncDec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class FileEncryption {

    //TODO Handle exceptions in a better way
    public static void encryptFile(String inputPath, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, BadPaddingException, IllegalBlockSizeException {
        Scanner inStream = new Scanner(System.in);

        // file to be encrypted
        FileInputStream inFile;
//        System.out.print("Enter the file path: ");
//        inputPath = inStream.nextLine();
        try {
            inFile = new FileInputStream(inputPath);
        }
        catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            System.out.println("Incorrect file path. Exception: " + e);
            return;
        }

        // password to encrypt the file
//        System.out.print("Enter password: ");
//        String password = inStream.nextLine();

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

        System.out.println("File Encrypted.");
    }

    public static void main(String[] args) throws Exception {

        Scanner inStream = new Scanner(System.in);

        // file to be encrypted
        FileInputStream inFile;
        System.out.print("Enter the file path: ");
        String inputPath = inStream.nextLine();
        try {
            inFile = new FileInputStream(inputPath);
        }
        catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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

        System.out.println("File Encrypted.");

    }

}
