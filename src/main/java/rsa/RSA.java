package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Class implementing the EncDecMVC.rsa algorithm.
 */
public class RSA {

    /**
     * Provides randomly generated RSA keys in string form.
     * Implements the <code>RSA</code> algorithm.
     */
    public static String getRandomKeys() {

        BigInteger p = pickPrime();
        BigInteger q = pickPrime();

        BigInteger n = p.multiply(q);
        BigInteger fin = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;
        BigInteger[][] EEtable;
        do {
            e = new BigInteger(fin.bitLength(), new Random());
            EEtable = rsa.GCD.getTable(fin, e);
        } while ((e.compareTo(fin) >= 0 || e.compareTo(BigInteger.ONE) <= 0) || !(EEtable[0][EEtable[0].length - 1]).equals(BigInteger.ONE));

        BigInteger d = EEtable[3][EEtable[3].length - 1];
        while (d.compareTo(BigInteger.ZERO) < 0)
            d = d.add(fin);

        return "Private key (d):        " + d + "\n" + "Public exponent (e):    " + e + "\n" +
                "Modulus (n):            " + n + "\n";
    }

    /**
     * Returns a random big prime.
     */
    public static BigInteger pickPrime() {

        int bitLength = 512;
        BigInteger rand = new BigInteger(bitLength, new Random());
        rand = rand.setBit(0);
        rand = rand.setBit(bitLength - 1);
        do
            rand = rand.add(BigInteger.TWO);
        while (!rsa.PrimalityTest.isPrime(rand, 10));

        return rand;
    }

    /**
     * Implements the EncDecMVC.rsa algorithm to generate random keys and encodes/decodes with it.
     * Uses the Chinese remainder theorem from the
     * {@link #decodeEncrypted(BigInteger[], BigInteger, BigInteger, BigInteger)} method
     * and the Extended Euclidean Algorithm from the <code>GCD</code> class.
     *
     * @param m the message to be encoded/decoded
     * @return the <code>m</code> string encoded and decoded back
     */
    public static String encodeDecode(String m) {
        BigInteger p = pickPrime();
        BigInteger q = pickPrime();

        BigInteger n = p.multiply(q);
        BigInteger fin = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;
        BigInteger[][] EEtable;
        do {
            e = new BigInteger(fin.bitLength(), new Random());
            EEtable = rsa.GCD.getTable(fin, e);
        } while ((e.compareTo(fin) >= 0 || e.compareTo(BigInteger.ONE) <= 0) || !(EEtable[0][EEtable[0].length - 1]).equals(BigInteger.ONE));

        BigInteger d = EEtable[3][EEtable[3].length - 1];
        while (d.compareTo(BigInteger.ZERO) < 0)
            d = d.add(fin);

        System.out.println("The message:    " + m);
        System.out.print("The message in ASCII:    ");
        printAscii(m);
        BigInteger[] c = encodeMessage(m, e, n);
        System.out.print("The message encoded:    ");
        printC(c);
        c = decodeEncrypted(c, d, p, q);
        System.out.print("The message decoded:    ");
        printC(c);
        System.out.print("The message:    ");
        printChar(c);

        return ASCIIToString(c);
    }

    /**
     * Prints the given array of integers of ASCII codes as the corresponding text.
     */
    static void printChar(BigInteger[] c) {
        for (int i = 0; i < c.length; i++)
            System.out.print((char) c[i].intValue());
        System.out.println();
    }

    /**
     * Prints the given <code>BigInteger array</code>. *
     *
     * @param c the message, represented with ASCII codes
     */
    static void printC(BigInteger[] c) {
        for (BigInteger i : c)
            System.out.print(i + " ");
        System.out.println();
    }

    /**
     * Converts the <code>m</code> string to an array of ASCII codes.
     */
    static BigInteger[] stringToArray(String m) {
        BigInteger[] c = new BigInteger[m.length()];
        for (int i = 0; i < m.length(); i++)
            c[i] = BigInteger.valueOf(m.charAt(i));

        return c;
    }

    /**
     * Converts the <code>c</code> array of ASCII codes to string.
     */
    static String ASCIIToString(BigInteger[] c) {
        String m = "";
        for (BigInteger bigInteger : c) m = m + (char) bigInteger.intValue();
        return m;
    }

    /**
     * Prints the <code>m</code> string as ASCII codes.
     */
    static void printAscii(String m) {
        for (int i = 0; i < m.length(); i++)
            System.out.print((int) m.charAt(i) + " ");
        System.out.println();
    }

    /**
     * Encodes the <code>m</code> message using the <code>c^e mod n</code> formula.
     * It represents the message as ASCII codes.
     *
     * @param m the message
     * @param e the exponent
     * @param n the modulus
     * @return the encoded message as ASCII codes
     */
    static BigInteger[] encodeMessage(String m, BigInteger e, BigInteger n) {
        BigInteger[] c = stringToArray(m);
        for (int i = 0; i < c.length; i++)
            c[i] = rsa.Exponentiation.modPower(c[i], e, n);

        return c;
    }

    /**
     * Method implementing the Chinese remainder theorem for decoding.
     *
     * @param c the encoded message, represented with ASCII codes
     * @param d the private key to decode the message
     * @param p random big prime from the EncDecMVC.rsa algorithm
     * @param q random big prime from the EncDecMVC.rsa algorithm
     * @return the message decoded, represented with ASCII codes
     */
    static BigInteger[] decodeEncrypted(BigInteger[] c, BigInteger d, BigInteger p, BigInteger q) {
        BigInteger cp, cq;
        BigInteger[][] table = rsa.GCD.getTable(p, q);
        for (int i = 0; i < c.length; i++) {
            cp = rsa.Exponentiation.modPower(c[i], d.mod(p.subtract(BigInteger.ONE)), p);
            cq = rsa.Exponentiation.modPower(c[i], d.mod(q.subtract(BigInteger.ONE)), q);
            c[i] = (p.multiply(table[2][table[2].length - 1]).multiply(cp)).add(q.multiply(table[3][table[3].length - 1]).multiply(cq)).mod(BigInteger.valueOf(p*q));
        }

        return c;
    }


    public static void main(String[] args) {

    }
}
