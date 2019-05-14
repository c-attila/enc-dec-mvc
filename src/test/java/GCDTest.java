import org.junit.jupiter.api.Assertions;
import rsa.GCD;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

public class GCDTest {

    @Test
    public void gcdWithBothZeroValuesNotDefined() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            GCD.getTable(BigInteger.ZERO, BigInteger.ZERO);
        });
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
