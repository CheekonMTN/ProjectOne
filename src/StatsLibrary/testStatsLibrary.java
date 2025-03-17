package StatsLibrary;

public class testStatsLibrary {
    public static void main(String[] args){
        statisticsLibrary tester = new statisticsLibrary();
        int[] someNumbers = {2, 1, 3, 2, 2, 2, 5, 3, 3, 3};
        System.out.println("Our Mean result: " + tester.getMean(someNumbers));
        System.out.println("Our Median result: " + tester.getMedian(someNumbers));
        System.out.print(("Our Mode result: " + tester.getMode(someNumbers)));
        System.out.println("Our Standard Deviation result: " + tester.getStdDeviation(someNumbers));
    }
}
