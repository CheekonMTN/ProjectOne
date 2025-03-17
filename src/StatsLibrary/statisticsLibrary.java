package StatsLibrary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static StatsLibrary.cpSolver.combination;

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

    // method to find the union of two lists (combines them without duplicates)
    public static ArrayList<String> findUnion(ArrayList<String> firstList, ArrayList<String> secondList) {
        ArrayList<String> unionResult = new ArrayList<>(firstList); // copy firstList into a new list

        for (String element : secondList) { // go through each item in secondList
            if (!unionResult.contains(element)) { // if it's not already in unionResult, add it
                unionResult.add(element);
            }
        }
        return unionResult; // return the final list with all unique elements
    }

    // method to find the intersection (common elements between both lists)
    public static ArrayList<String> findIntersection(ArrayList<String> firstList, ArrayList<String> secondList) {
        ArrayList<String> intersectionResult = new ArrayList<>(); // new empty list to store common items

        for (String element : firstList) { // go through firstList
            if (secondList.contains(element)) { // if secondList also has it, add to intersectionResult
                intersectionResult.add(element);
            }
        }
        return intersectionResult; // return the list of common elements
    }

    // method to find the complement (stuff in universalSet that isn't in subset)
    public static ArrayList<String> findComplement(ArrayList<String> universalSet, ArrayList<String> subset) {
        ArrayList<String> complementResult = new ArrayList<>(); // new list for missing items

        for (String element : universalSet) { // go through universal set
            if (!subset.contains(element)) { // if it's NOT in subset, add it to complement
                complementResult.add(element);
            }
        }
        return complementResult; // return the complement list
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
        BigInteger combinations = combination(n, r);
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
    public static double geometric(int k, double p) {
        return Math.pow(1 - p, k - 1) * p;
    }
    // method that calculates geometric expected value
    public static double geometricExpected(double p) {
        if (p <= 0 || p > 1) {
            return -1;
        }
        return 1 / p;
    }

    // method that calculates geometric variance
    public static double geometricVariance(double p) {
        if (p <= 0 || p > 1) {
            return -1;
        }
        return (1 - p) / (p * p);
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
    // method that calculates hypergeometric probability
    public static double hypergeometric(int N, int K, int n, int k) {
        if (k > K || k > n || n > N || K > N) {
            return 0.0;
        }

        BigInteger numerator = combination(K, k).multiply(combination(N - K, n - k));
        BigInteger denominator = combination(N, n);

        return numerator.doubleValue() / denominator.doubleValue();
    }

    // method that calculates hypergeometric expected value
    public static double hypergeometricExpected(int N, int K, int n) {
        return n * ((double) K / N);
    }

    // method that calculates hypergeometric variance
    public static double hypergeometricVariance(int N, int K, int n) {
        double p = (double) K / N;
        return n * p * (1 - p) * ((N - n) / (N - 1));
    }

    // method that calculates negative binomial probability (r successes, k trials, probability p)
    public static double negativeBinomial(int r, int k, double p) {
        if (p <= 0 || p > 1 || k < r) {
            return 0.0;
        }
        BigInteger combinations = combination(k - 1, r - 1);
        return combinations.doubleValue() * Math.pow(p, r) * Math.pow(1 - p, k - r);
    }

    // method that calculates negative binomial expected value
    public static double negativeBinomialExpected(int r, double p) {
        if (p <= 0 || p > 1) {
            return -1;
        }
        return r / p;
    }

    // method that calculates negative binomial variance
    public static double negativeBinomialVariance(int r, double p) {
        if (p <= 0 || p > 1) {
            return -1;
        }
        return (r * (1 - p)) / (p * p);
    }
}