package StatsLibrary;

import java.math.BigInteger;

public class cpSolver {

    // Recursive factorial method using BigInteger
    public static BigInteger factorial(int n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE; // Base case
        }
        return BigInteger.valueOf(n).multiply(factorial(n - 1)); // Recursive case
    }

    // Method to compute permutations P(n, r) = n! / (n-r)!
    public static BigInteger permutation(int n, int r) {
        if (r > n)
            return BigInteger.ZERO; // Invalid case
        return factorial(n).divide(factorial(n - r));
    }

    // Method to compute combinations C(n, r) = n! / (r! * (n-r)!)
    public static BigInteger combination(int n, int r) {
        if (r > n)
            return BigInteger.ZERO; // Invalid case
        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    public static void main(String[] args) {
        // Input values (Replace these with any numbers you want)
        int num = 6; // Change this to any number
        int r = 3;   // Change this as well

        System.out.println("Factorial of " + num + " (" + num + "!) = " + factorial(num));
        System.out.println("Permutation P(" + num + ", " + r + ") = " + permutation(num, r));
        System.out.println("Combination C(" + num + ", " + r + ") = " + combination(num, r));
    }
}