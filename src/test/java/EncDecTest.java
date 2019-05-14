import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

public class EncDecTest {

    @Test
    public void testTextFile() throws IOException {

        final File original = new File(getClass().getResource("plainfile-original.txt").getFile());
        final File copy = new File(getClass().getResource("plainfile-copy.txt").getFile());
        FileUtils.copyFile(original, copy);

        try {
            System.out.println("Encrypting" + copy.getPath());
            encdec.FileEncryption.encryptFile(copy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Decrypting");
            File encCopy = new File(getClass().getResource("plainfile-copy.txt" + ".enc").getFile());
            System.out.println("encCopy file created" + encCopy.getPath());
            encdec.FileDecryption.decryptFile(encCopy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        assertTrue(FileUtils.contentEquals(original, copy));
    }

    @Test
    public void testImageFile() throws IOException {

        final File original = new File(getClass().getResource("plainfile-original.jpg").getFile());
        final File copy = new File(getClass().getResource("plainfile-copy.jpg").getFile());
        FileUtils.copyFile(original, copy);

        try {
            System.out.println("Encrypting" + copy.getPath());
            encdec.FileEncryption.encryptFile(copy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Decrypting");
            File encCopy = new File(getClass().getResource("plainfile-copy.jpg" + ".enc").getFile());
            System.out.println("encCopy file created" + encCopy.getPath());
            encdec.FileDecryption.decryptFile(encCopy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        assertTrue(FileUtils.contentEquals(original, copy));
    }

    @Test
    public void testAudioFile() throws IOException {

        final File original = new File(getClass().getResource("The_Impossible-Savfk-original.mp3").getFile());
        final File copy = new File(getClass().getResource("The_Impossible-Savfk-copy.mp3").getFile());
        FileUtils.copyFile(original, copy);

        try {
            System.out.println("Encrypting" + copy.getPath());
            encdec.FileEncryption.encryptFile(copy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Decrypting");
            File encCopy = new File(getClass().getResource("The_Impossible-Savfk-copy.mp3" + ".enc").getFile());
            System.out.println("encCopy file created" + encCopy.getPath());
            encdec.FileDecryption.decryptFile(encCopy.getPath(), "test");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        assertTrue(FileUtils.contentEquals(original, copy));
    }

    @Test(expected = FileNotFoundException.class)
    public void testWrongFilePathEncryption() throws Exception {
        encdec.FileEncryption.encryptFile("test", "test");
    }

    @Test(expected = FileNotFoundException.class)
    public void testWrongFilePathDecryption() throws Exception {
        encdec.FileDecryption.decryptFile("test", "test");
    }
}