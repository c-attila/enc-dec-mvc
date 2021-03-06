import org.junit.jupiter.api.Test;
import rsa.PrimalityTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class primalityTestTest {

    @Test
    public void testingNegativeNumbersAndZero() {
        assertFalse(PrimalityTest.isPrime(BigInteger.ZERO, 10));
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(-1), 10));
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(-6), 10));
    }

    @Test
    public void twoIsPrime() {
        assertTrue(PrimalityTest.isPrime(BigInteger.valueOf(2), 10));
    }

    @Test
    public void testingPseudoprimes() {
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(264239), 10));
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(124882), 10));
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(58892), 10));
        assertFalse(PrimalityTest.isPrime(BigInteger.valueOf(19279), 10));
    }

    @Test
    public void testingLargePrimes() {
        assertTrue(PrimalityTest.isPrime(new BigInteger("20988936657440586486151264256610222593863921"), 10));
        assertTrue(PrimalityTest.isPrime(new BigInteger("170141183460469231731687303715884105727"), 10));
        assertTrue(PrimalityTest.isPrime(new BigInteger("67280421310721"), 10));
        assertTrue(PrimalityTest.isPrime(new BigInteger("2147483647"), 10));
    }
}
