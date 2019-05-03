package RSA;

import java.math.BigInteger;
import java.util.Random;

/** Primality test, implementing the Miller-Rabin algorithm. */
public class primalityTest {

    /**
     * This function is called for all k trials. It returns false if n is composite
     * and returns false if n is probably prime.
     *
     * @param d an odd number such that d*2^r = n-1 for some r >= 1
     * @param n the number to be tested
     * @return true if <code>n</code> is probably prime, false if <code>n</code> is composite
     */
    static boolean millerTest(BigInteger d, BigInteger n) {

        // Pick a random number in [2..n-2]
        // Corner cases make sure that n > 4
        BigInteger a;
        do {
            a = new BigInteger(n.subtract(BigInteger.ONE).bitLength(), new Random());
        } while ((n.subtract(BigInteger.ONE).compareTo(BigInteger.valueOf(2)) > 0 ? a.compareTo(n.subtract(BigInteger.ONE)) >= 0 : a.compareTo(BigInteger.valueOf(3)) >= 0) || a.compareTo(BigInteger.valueOf(2)) < 0);


        // Compute a^d % n
        BigInteger x = Exponentiation.modPower(a, d, n);

        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            return true;

        // Keep squaring x while one of the
        // following doesn't happen
        // (i) d does not reach n-1
        // (ii) (x^2) % n is not 1
        // (iii) (x^2) % n is not n-1
        while (!d.equals(n.subtract(BigInteger.ONE))) {
            x = Exponentiation.modPower(x, BigInteger.TWO, n);
            d = d.multiply(BigInteger.TWO);

            if (x.equals(BigInteger.ONE))
                return false;
            if (x.equals(n.subtract(BigInteger.ONE)))
                return true;
        }

        // Return composite
        return false;
    }

    // It returns false if n is composite
    // and returns true if n is probably
    // prime. k is an input parameter that
    // determines accuracy level. Higher
    // value of k indicates more accuracy.

    /**
     * Probes the probability of the given number being prime.
     *
     * @param n the given number to prove prime
     * @param k determines the accuracy level
     * @return false if <code>n</code> is composite, true if <code>n</code> is probably prime
     */
    public static boolean isPrime(BigInteger n, int k) {

        // Corner cases
        if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4)))
            return false;
        if (n.compareTo(BigInteger.valueOf(3)) <= 0)
            return true;

        // Find r such that n = 2^d * r + 1
        // for some r >= 1
        BigInteger d = n.subtract(BigInteger.ONE);

        while (Exponentiation.modPower(d, BigInteger.ONE, BigInteger.TWO).equals(BigInteger.ZERO))
            d = d.divide(BigInteger.TWO);

        // Iterate given nber of 'k' times
        for (int i = 0; i < k; i++)
            if (!millerTest(d, n))
                return false;

        return true;
    }

    public static void main(String[] args) {

    }
}
