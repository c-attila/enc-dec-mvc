package RSA;

import java.math.BigInteger;

public class primalityTestTester {

    public static void main(String[] args) {
        System.out.println("Pseudoprimes:");
        System.out.println(primalityTest.isPrime(BigInteger.valueOf(264239), 10));
        System.out.println(primalityTest.isPrime(BigInteger.valueOf(124882), 10));
        System.out.println(primalityTest.isPrime(BigInteger.valueOf(58892), 10));
        System.out.println(primalityTest.isPrime(BigInteger.valueOf(19279), 10));
        System.out.println();
        System.out.println("Large primes:");
        System.out.println(primalityTest.isPrime(new BigInteger("20988936657440586486151264256610222593863921"), 10));
        System.out.println(primalityTest.isPrime(new BigInteger("170141183460469231731687303715884105727"), 10));
        System.out.println(primalityTest.isPrime(new BigInteger("67280421310721"), 10));
        System.out.println(primalityTest.isPrime(new BigInteger("2147483647"), 10));
        BigInteger M127 = new BigInteger("170141183460469231731687303715884105727");
        BigInteger big;
        big = (BigInteger.valueOf(180).multiply((M127).pow(2))).add(BigInteger.ONE);
        System.out.println(primalityTest.isPrime(big, 10));
    }
}
