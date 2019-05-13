import rsa.GCD;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigInteger;

public class GCDTest {

    @Test(expected = ArithmeticException.class)
    public void gcdWithBothZeroValuesNotDefined() {
        BigInteger[][] table = GCD.getTable(BigInteger.ZERO, BigInteger.ZERO);
        assertEquals(BigInteger.ZERO, table[0][table[0].length - 1]);
    }

    @Test
    public void gcdWithOneZeroShouldBeTheOtherValue() {
        BigInteger[][] table = GCD.getTable(BigInteger.valueOf(42), BigInteger.valueOf(0));
        assertEquals(BigInteger.valueOf(42), table[0][table[0].length - 1]);
        table = GCD.getTable(BigInteger.valueOf(0), BigInteger.valueOf(42));
        assertEquals(BigInteger.valueOf(42), table[0][table[0].length - 1]);
    }

    @Test
    public void testingWithIntegers() {
        BigInteger[][] table = GCD.getTable(BigInteger.valueOf(252252), BigInteger.valueOf(25));
        assertEquals(BigInteger.ONE, table[0][table[0].length - 1]);
    }
}
