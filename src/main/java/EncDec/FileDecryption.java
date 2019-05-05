package EncDec;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FileDecryption {

    //TODO Handle exceptions in a better way
    public static void decryptFile(String inputPath, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Scanner inStream = new Scanner(System.in);

        FileInputStream fis;
//        System.out.print("Enter the file path: ");
//        String inputPath = inStream.nextLine();
        try {
            fis = new FileInputStream(inputPath);
        }
        catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        }
        catch (Exception e) {
            System.out.println("File couldn't be located. Exception: " + e);
            return;
        }

//        System.out.print("Enter password: ");
//        String password = inStream.nextLine();

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
        }
        catch (Exception e) {
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
        }
        catch (BadPaddingException e) {
            System.out.println("Incorrect password.");
            return;
        }
        catch (Exception e) {
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

    public static void main(String[] args) throws Exception {

        Scanner inStream = new Scanner(System.in);

        FileInputStream fis;
        System.out.print("Enter the file path: ");
        String inputPath = inStream.nextLine();
        try {
            fis = new FileInputStream(inputPath);
        }
        catch (FileNotFoundException e) {
            System.out.println("File note found.");
            return;
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (BadPaddingException e) {
            System.out.println("Incorrect password.");
            return;
        }
        catch (Exception e) {
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