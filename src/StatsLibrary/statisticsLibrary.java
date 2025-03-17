package StatsLibrary;

import java.math.BigInteger;
import java.util.Arrays;

public class statisticsLibrary {
    // method that returns the mean/average of an array of ints
    public double getMean(int[] numbers) {
        long total = 0;
        for (int number : numbers) {
            total += number;
        }
        return (double) total / numbers.length;
    }

    // method that returns the middle value(s) of a sorted array of ints
    public double getMedian(int[] numbers) {
        Arrays.sort(numbers);
        int length = numbers.length;

        if (length % 2 == 0) {
            int mid1 = numbers[length / 2 - 1];
            int mid2 = numbers[length / 2];
            return (double) (mid1 + mid2) / 2;
        }
        return numbers[length / 2];
    }

    // method that returns the most frequently occurring value in an array of ints
    public BigInteger getMode(int[] numbers) {
        Arrays.sort(numbers);
        BigInteger mode = BigInteger.valueOf(numbers[0]);
        int maxCount = 1;
        int currentCount = 1;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1]) {
                currentCount++;
            } else {
                currentCount = 1;
            }

            if (currentCount > maxCount) {
                maxCount = currentCount;
                mode = BigInteger.valueOf(numbers[i]);
            }
        }
        return mode;
    }

    // method that returns the standard deviation of an array of ints
    public double getStdDeviation(int[] numbers) {
        double mean = getMean(numbers);
        double sum = 0;

        for (int num : numbers) {
            sum += Math.pow(num - mean, 2);
        }
        return Math.sqrt(sum / numbers.length);
    }

    // method that returns the product of two probabilities if they are independent
    public static double multiplicativeLaw(double pA, double pB, boolean independent) {
        if (independent) {
            return pA * pB;
        }
        return -1;
    }

    // method that returns the sum of two probabilities minus their intersection
    public static double additiveLaw(double pA, double pB, double pAB) {
        return pA + pB - pAB;
    }

    // method that checks if two events are independent based on their probabilities
    public static boolean areIndependent(double pA, double pB, double pAB) {
        return Math.abs(pAB - (pA * pB)) < 1e-6;
    }

    // method that calculates the conditional probability of A given B
    public static double conditionalProbability(double pAB, double pB) {
        if (pB != 0) {
            return pAB / pB;
        }
        return -1;
    }

    // method that calculates the number of combinations of n items taken r at a time
    public static BigInteger combination(int n, int r) {
        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    // method that calculates the number of permutations of n items taken r at a time
    public static BigInteger permutation(int n, int r) {
        return factorial(n).divide(factorial(n - r));
    }

    // method that calculates the probability of A given B using Bayes' theorem
    public static double bayesTheorem(double pBA, double pA, double pB) {
        if (pB == 0) {
            return -1;
        }
        return (pBA * pA) / pB;
    }

    // method that calculates the variance of an array of doubles
    public static double variance(double[] values) {
        double mean = Arrays.stream(values).average().orElse(0);
        double meanSquared = Arrays.stream(values).map(v -> v * v).average().orElse(0);
        return meanSquared - Math.pow(mean, 2);
    }

    // method that calculates the binomial probability using combination and probability
    public static BigInteger binomialProbability(int n, int r, double p) {
        BigInteger combinations = cpSolver.combination(n, r);
        double probability = Math.pow(p, r) * Math.pow(1 - p, n - r);
        return combinations.multiply(BigInteger.valueOf((long)(probability * 1000000)))
                .divide(BigInteger.valueOf(1000000));
    }

    // method that calculates the expected value of a binomial distribution
    public static double binomialExpected(int n, double p) {
        return n * p;
    }

    // method that calculates the variance of a binomial distribution
    public static double binomialVariance(int n, double p) {
        return n * p * (1 - p);
    }

    // method that calculates the geometric probability mass function
    public static double geometricPMF(int k, double p) {
        return Math.pow(1 - p, k - 1) * p;
    }

    // method that calculates the factorial of a number using BigInteger
    private static BigInteger factorial(int n) {
        if (n <= 1) {
            return BigInteger.ONE;
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}