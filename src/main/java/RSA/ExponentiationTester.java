package RSA;

import java.math.BigInteger;
import java.util.Arrays;

public class ExponentiationTester {

    public static void main(String[] args) {
        System.out.println(Exponentiation.getExponents(BigInteger.valueOf(97)));
        System.out.println(Arrays.toString(Exponentiation.getExponentRemainders(Exponentiation.getGreatestExponent(BigInteger.valueOf(97)), BigInteger.valueOf(127), BigInteger.valueOf(341))));
        System.out.println(Exponentiation.getRemainder(BigInteger.valueOf(127), BigInteger.valueOf(97), BigInteger.valueOf(341)));
        System.out.println(Exponentiation.getRemainder(BigInteger.valueOf(6), BigInteger.valueOf(73), BigInteger.valueOf(100)));
        System.out.println(Exponentiation.modPower(BigInteger.valueOf(127), BigInteger.valueOf(97), BigInteger.valueOf(341)));
        System.out.println(Exponentiation.modPower(BigInteger.valueOf(6), BigInteger.valueOf(73), BigInteger.valueOf(100)));
    }
}
