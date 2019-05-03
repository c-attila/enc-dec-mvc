package RSA;

import java.math.BigInteger;

public class GCDTester {

    public static void main(String[] args) {

        GCD.printTable(GCD.getTable(BigInteger.valueOf(49140), BigInteger.valueOf(643)));
        System.out.println();
        GCD.printTable(GCD.getTable(BigInteger.valueOf(252252), BigInteger.valueOf(25)));
        System.out.println();
        GCD.printTable(GCD.getTable(BigInteger.valueOf(47), BigInteger.valueOf(31)));
        System.out.println();

    }

}
