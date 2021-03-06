package encdec;

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

        FileInputStream inFileStream = getInputFileStream(inputPath);

        FileOutputStream outFileStream = getOutputFileStream(inputPath);

        byte[] salt = getSalt();

        SecretKey secret = getSecretKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);

        writeIV(cipher);

        writeEncrypted(inFileStream, outFileStream, cipher);
    }

    /**
     * Provides FileInputStream for the given path.
     *
     * @param inputPath the given path
     * @return the <code>FileInputStream</code> object
     */
    private static FileInputStream getInputFileStream(String inputPath) throws FileNotFoundException {
        return new FileInputStream(inputPath);
    }

    /**
     * Provides FileOutputStream for the given path.
     *
     * @param inputPath the given path
     * @return the <code>FileOutputStream</code> instance
     */
    private static FileOutputStream getOutputFileStream(String inputPath) {
        try {
            List<Integer> lastIndexOfSeparator = Arrays.asList
                    (inputPath.lastIndexOf('/'), inputPath.lastIndexOf('\\'), inputPath.lastIndexOf(':'));
            int lastIndex = lastIndexOfSeparator.stream().max(Comparator.comparingInt(x -> x)).get();
            String outputPath = inputPath.substring(0, lastIndex + 1);
            return new FileOutputStream(outputPath + inputPath.substring(lastIndex + 1) + ".enc");
        } catch (Exception e) {
            logger.error("Incorrect file path. Exception: " + e);
            return null;
        }
    }

    /**
     * Provides a random salt of <code>byte arrays</code>.
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
    private static void writeEncrypted(FileInputStream inFileStream, FileOutputStream outFileStream, Cipher cipher)
            throws IOException, BadPaddingException, IllegalBlockSizeException {

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
    }

    public static void main(String[] args) {
    }

}
