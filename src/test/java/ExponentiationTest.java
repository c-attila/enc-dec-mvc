import rsa.Exponentiation;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigInteger;

public class ExponentiationTest {

    @Test
    public void remainderWithZeroAsBaseShouldBeZero() {
        assertEquals(BigInteger.ZERO, Exponentiation.modPower(BigInteger.ZERO, BigInteger.TWO, BigInteger.TEN));
        assertEquals(BigInteger.ZERO, Exponentiation.getRemainder(BigInteger.ZERO, BigInteger.TWO, BigInteger.TEN));
    }

    @Test(expected = ArithmeticException.class)
    public void modulusOfTheExponentiationShouldBePositive() {
        assertEquals(BigInteger.ZERO, Exponentiation.modPower(BigInteger.TWO, BigInteger.TWO, BigInteger.ZERO));
        assertEquals(BigInteger.ZERO, Exponentiation.getRemainder(BigInteger.TWO, BigInteger.TWO, BigInteger.ZERO));
        assertEquals(BigInteger.ZERO, Exponentiation.modPower(BigInteger.TWO, BigInteger.TWO, BigInteger.valueOf(-1)));
        assertEquals(BigInteger.ZERO, Exponentiation.getRemainder(BigInteger.TWO, BigInteger.TWO, BigInteger.valueOf(-1)));
    }

    @Test
    public void testingWithIntegers() {
        assertEquals(BigInteger.valueOf(327), Exponentiation.modPower(BigInteger.valueOf(127), BigInteger.valueOf(97),
                BigInteger.valueOf(341)));
        assertEquals(BigInteger.valueOf(327), Exponentiation.getRemainder(BigInteger.valueOf(127), BigInteger.valueOf(97),
                BigInteger.valueOf(341)));
    }

}
