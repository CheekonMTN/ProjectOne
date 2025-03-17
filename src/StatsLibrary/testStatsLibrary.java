package StatsLibrary;

public class testStatsLibrary {
    public static void main(String[] args) {
        testBasicStats();
        testCPFormulas();
        testProbabilityDistributions();
    }

    private static void testBasicStats() {
        statisticsLibrary stats = new statisticsLibrary();
        int[] data = {2, 1, 3, 2, 2, 2, 5, 3, 3, 3};

        System.out.println("Basic Statistics");
        System.out.println("Mean = " + stats.getMean(data));
        System.out.println("Median = " + stats.getMedian(data));
        System.out.println("Mode = " + stats.getMode(data));
        System.out.println("Std Dev = " + stats.getStdDeviation(data));
        System.out.println();
    }

    private static void testCPFormulas() {
        cpSolver cp = new cpSolver();

        System.out.println("Combinations & Permutations");
        System.out.println("C(5,2) = " + cp.combination(5, 2));
        System.out.println("C(10,3) = " + cp.combination(10, 3));
        System.out.println("P(5,2) = " + cp.permutation(5, 2));
        System.out.println("P(10,3) = " + cp.permutation(10, 3));
        System.out.println();
    }

    private static void testProbabilityDistributions() {
        statisticsLibrary stats = new statisticsLibrary();
        double p = 0.3;
        int r = 3;

        System.out.println("Probability Distributions");

        // Binomial tests
        System.out.println("Binomial (n=10, p=0.3)");
        System.out.println("P(X=3) = " + stats.binomialProbability(10, 3, p));
        System.out.println("E(X) = " + stats.binomialExpected(10, p));
        System.out.println("Var(X) = " + stats.binomialVariance(10, p));
        System.out.println();

        // Geometric tests
        System.out.println("Geometric (p=0.3)");
        System.out.println("P(X=3) = " + stats.geometric(3, p));
        System.out.println("E(X) = " + stats.geometricExpected(p));
        System.out.println("Var(X) = " + stats.geometricVariance(p));
        System.out.println();

        // Hypergeometric tests
        System.out.println("Hypergeometric (N=50, K=20, n=10)");
        System.out.println("P(X=4) = " + stats.hypergeometric(50, 20, 10, 4));
        System.out.println("E(X) = " + stats.hypergeometricExpected(50, 20, 10));
        System.out.println("Var(X) = " + stats.hypergeometricVariance(50, 20, 10));
        System.out.println();

        // Negative Binomial tests
        System.out.println("Negative Binomial (r=3, p=0.3)");
        System.out.println("P(X=5) = " + stats.negativeBinomial(r, 5, p));
        System.out.println("E(X) = " + stats.negativeBinomialExpected(r, p));
        System.out.println("Var(X) = " + stats.negativeBinomialVariance(r, p));
    }
}