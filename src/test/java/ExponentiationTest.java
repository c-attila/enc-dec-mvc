import org.junit.jupiter.api.Assertions;
import rsa.Exponentiation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;


public class ExponentiationTest {

    @Test
    public void remainderWithZeroAsBaseShouldBeZero() {
        assertEquals(BigInteger.ZERO, Exponentiation.modPower(BigInteger.ZERO, BigInteger.TWO, BigInteger.TEN));
        assertEquals(BigInteger.ZERO, Exponentiation.getRemainder(BigInteger.ZERO, BigInteger.TWO, BigInteger.TEN));
    }

    @Test
    public void modulusOfTheExponentiationShouldBePositive() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Exponentiation.modPower(BigInteger.TWO, BigInteger.TWO, BigInteger.ZERO);
        });
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Exponentiation.getRemainder(BigInteger.TWO, BigInteger.TWO, BigInteger.ZERO);
        });
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Exponentiation.modPower(BigInteger.TWO, BigInteger.TWO, BigInteger.valueOf(-1));
        });
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Exponentiation.getRemainder(BigInteger.TWO, BigInteger.TWO, BigInteger.valueOf(-1));
        });
    }

    @Test
    public void testingWithIntegers() {
        assertEquals(BigInteger.valueOf(327), Exponentiation.modPower(BigInteger.valueOf(127), BigInteger.valueOf(97),
                BigInteger.valueOf(341)));
        assertEquals(BigInteger.valueOf(327), Exponentiation.getRemainder(BigInteger.valueOf(127), BigInteger.valueOf(97),
                BigInteger.valueOf(341)));
    }

}
