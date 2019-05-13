package rsa;

import encdec.FileEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the modular exponentiation algorithm.
 */
public class Exponentiation {

    /**
     * <code>Logger</code> instance for logging.
     */
    private static Logger logger = LogManager.getLogger(Exponentiation.class);

    /**
     * Returns the remainder when an integer x raised to the yth power, x^y, is divided by an integer n.
     *
     * @param x the base of the exponentiation
     * @param y the exponent of the exponentiation
     * @param n the modulus of the exponentiation
     * @return the remainder of the exponentiation
     */
    public static BigInteger modPower(BigInteger x, BigInteger y, BigInteger n) {

        BigInteger res = BigInteger.ONE;

        x = x.mod(n);

        while (y.compareTo(BigInteger.ZERO) > 0) {

            if (y.and(BigInteger.ONE).equals(BigInteger.ONE))
                res = res.multiply(x).mod(n);

            y = y.shiftRight(1);
            x = x.multiply(x).mod(n);
        }

        return res;
    }

    /**
     * Driver of the modular exponentiation.
     * Returns the remainder when an integer g raised to the eth power, g^e, is divided by an integer n.
     *
     * @param g the base integer
     * @param e the exponent
     * @param n the modulus
     * @return the remainder of the exponentiation
     */
    public static BigInteger getRemainder(BigInteger g, BigInteger e, BigInteger n) {
        if (g.equals(BigInteger.ZERO))
            return BigInteger.ZERO;
        else if (n.compareTo(BigInteger.ZERO) <= 0)
            throw new ArithmeticException("Modulus should be positive.");
        else {
            try {
                BigInteger product = getProduct(getExponents(e), getExponentRemainders(getGreatestExponent(e), g, n));
                return product.mod(n);
            } catch (ArithmeticException exc) {
                logger.error("Can't divide by 0.");
            }
            return BigInteger.valueOf(-1);
        }
    }

    /**
     * Multiplies all the exponents.
     *
     * @param exponents  the exponents, representing the remainders to be multiplied
     * @param remainders the remainders to be multiplied
     * @return the final result of the modular exponentiation.
     */
    static BigInteger getProduct(List<Integer> exponents, BigInteger[] remainders) {
        BigInteger product = BigInteger.ONE;

        for (int i : exponents) {
            product = product.multiply((remainders[i]));
        }

        return product;
    }

    /**
     * Calculates the powers of two of the given integer's representation.
     *
     * @param e the given integer
     * @return the exponents of two
     */
    private static List<Integer> getExponents(BigInteger e) {

        List<Integer> exponents = new ArrayList<>();
        int i = getGreatestExponent(e);

        exponents.add(i - 1 > 0 ? i - 1 : 0);
        e = e.subtract(BigInteger.valueOf((long) Math.pow(2, i - 1 > 0 ? i - 1 : 0)));
        int j = i - 2;
        while (j >= 0) {
            if (e.compareTo(BigInteger.valueOf((long) Math.pow(2, j))) >= 0) {
                exponents.add(j);
                e = e.subtract(BigInteger.valueOf((long) Math.pow(2, j)));
            }
            j--;
        }

        return exponents;
    }

    /**
     * Returns the greatest exponent of the parameter's binary representation.
     * Used for the method {@link #getExponents(BigInteger)}
     *
     * @param e the exponent
     * @return the greatest exponent
     */
    private static int getGreatestExponent(BigInteger e) {
        int i = 0;
        while (e.compareTo(BigInteger.valueOf((long) Math.pow(2, i))) >= 0) {
            i++;
        }
        return i;
    }

    /**
     * The remainders got from the given integer's division with remainder.
     *
     * @param greatestExponent greatest exponent got from {@link #getGreatestExponent(BigInteger)}
     * @param g                the given integer
     * @param n                the modulus used for the division with remainder
     * @return the remainders used to calculate the product with {@link #getProduct(List, BigInteger[])}
     */
    private static BigInteger[] getExponentRemainders(int greatestExponent, BigInteger g, BigInteger n) {
        BigInteger[] remainders = new BigInteger[greatestExponent];
        remainders[0] = g;
        for (int i = 1; i < greatestExponent; i++) {
            try {
                remainders[i] = (remainders[i - 1].pow(2)).mod(n);
            } catch (ArithmeticException e) {
                logger.error("Can't divide by 0.");
                break;
            }
        }

        return remainders;
    }

    public static void main(String[] args) {

    }
}
