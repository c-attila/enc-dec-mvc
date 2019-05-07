package RSA;

import java.math.BigInteger;

/**
 * Class implementing the Extended Euclidean algorithm.
 */
public class GCD {

    /**
     * Prints the 2D array <code>table</code>.
     *
     * @param table the table to be printed
     */
    static void printTable(BigInteger[][] table) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < table[i].length; j++)
                System.out.print(table[i][j] + " ");
            System.out.println();
        }
    }

    /**
     * Calculates the length of the <code>table</code> used for the EEA.
     *
     * @param r first parameter of the EEA
     * @param e second parameter of the EEA
     * @return the length for the EEA table
     */
    static int getLength(BigInteger r, BigInteger e) {
        int n = 0;
        BigInteger etemp;
        while (e.compareTo(BigInteger.ZERO) > 0) {
            etemp = e;
            e = r.mod(e);
            r = etemp;
            n++;
        }
        return n;
    }

    /**
     * Produces the <code>table</code> got from the EEA.
     * <code>table[2][length]</code> and <code>table[3][length]</code> is
     * already multiplied with -1^n and -1^(n+1).
     *
     * @param r first parameter of the EEA
     * @param e second parameter of the EEA
     * @return the <code>table</code>, produced by the EEA
     */
    public static BigInteger[][] getTable(BigInteger r, BigInteger e) {
        if (r.equals(BigInteger.ZERO) && e.equals(BigInteger.ZERO))
            throw new ArithmeticException("GCD of 0 and 0 is not defined.");
        else if (r.equals(BigInteger.ZERO)) {
            BigInteger[][] res = new BigInteger[1][1];
            res[0][0] = e;
            return res;
        } else if (e.equals(BigInteger.ZERO)) {
            BigInteger[][] res = new BigInteger[1][1];
            res[0][0] = r;
            return res;
        }
        int n = getLength(r, e);
        BigInteger[][] table = new BigInteger[4][n + 1];
        table[0][0] = r;
        table[0][1] = e;
        table[1][1] = r.divide(e);
        table[2][0] = BigInteger.ONE;
        table[2][1] = BigInteger.ZERO;
        table[3][0] = BigInteger.ZERO;
        table[3][1] = BigInteger.ONE;

        for (int i = 2; i < n + 1; i++) {
            table[0][i] = table[0][i - 2].mod(table[0][i - 1]);
            table[1][i] = table[0][i - 1].divide(table[0][i]);
            table[2][i] = (table[1][i - 1].multiply(table[2][i - 1])).add(table[2][i - 2]);
            table[3][i] = (table[1][i - 1].multiply(table[3][i - 1])).add(table[3][i - 2]);
        }
        table[2][n] = BigInteger.valueOf(-1).pow(n).multiply(table[2][n]);
        table[3][n] = BigInteger.valueOf(-1).pow(n + 1).multiply(table[3][n]);

        return table;
    }

    public static void main(String[] args) {

    }
}
