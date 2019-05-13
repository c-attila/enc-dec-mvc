package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Primality test, implementing the Miller-Rabin algorithm.
 */
public class PrimalityTest {

    /**
     * Probes the probability of the given number being prime.
     *
     * @param n the given number to prove prime
     * @param k determines the accuracy level
     * @return false if <code>n</code> is composite, true if <code>n</code> is probably prime
     */
    public static boolean isPrime(BigInteger n, int k) {

        if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4)))
            return false;
        if (n.compareTo(BigInteger.valueOf(3)) <= 0)
            return true;

        BigInteger d = n.subtract(BigInteger.ONE);

        while (rsa.Exponentiation.modPower(d, BigInteger.ONE, BigInteger.TWO).equals(BigInteger.ZERO))
            d = d.divide(BigInteger.TWO);

        for (int i = 0; i < k; i++)
            if (!millerTest(d, n))
                return false;

        return true;
    }

    /**
     * This function is called for all k trials. It returns false if n is composite
     * and returns false if n is probably prime.
     *
     * @param d an odd number such that d*2^r = n-1 for some r >= 1
     * @param n the number to be tested
     * @return true if <code>n</code> is probably prime, false if <code>n</code> is composite
     */
    private static boolean millerTest(BigInteger d, BigInteger n) {

        BigInteger a;
        do {
            a = new BigInteger(n.subtract(BigInteger.ONE).bitLength(), new Random());
        } while ((n.subtract(BigInteger.ONE).compareTo(BigInteger.valueOf(2)) > 0 ? a.compareTo(n.subtract(BigInteger.ONE)) >= 0 : a.compareTo(BigInteger.valueOf(3)) >= 0) || a.compareTo(BigInteger.valueOf(2)) < 0);

        BigInteger x = rsa.Exponentiation.modPower(a, d, n);

        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            return true;

        while (!d.equals(n.subtract(BigInteger.ONE))) {
            x = rsa.Exponentiation.modPower(x, BigInteger.TWO, n);
            d = d.multiply(BigInteger.TWO);

            if (x.equals(BigInteger.ONE))
                return false;
            if (x.equals(n.subtract(BigInteger.ONE)))
                return true;
        }

        return false;
    }

    public static void main(String[] args) {

    }
}
