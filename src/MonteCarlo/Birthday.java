package MonteCarlo;
import java.util.Random;

public class Birthday {
    private int numberOfPeople;
    private int numberOfTrials;

    public Birthday(int numberOfPeople, int numberOfTrials) {
        this.numberOfPeople = numberOfPeople;
        this.numberOfTrials = numberOfTrials;
    }

    public double findProbability() {
        int matchedBday = 0;
        for (int i = 0; i < numberOfTrials; i++) {
            if (hasSameBday()) {
                matchedBday++;
            }
        }
        return (double) matchedBday / numberOfTrials;
    }

    private boolean hasSameBday() {
        boolean[] usedBdays = new boolean[365]; // Keep track of taken birthdays
        Random random = new Random();

        for (int i = 0; i < numberOfPeople; i++) {
            int birthday = random.nextInt(365); // Pick a random birthday (0-364)
            if (usedBdays[birthday]) {
                return true; // Found a match
            }
            usedBdays[birthday] = true;
        }
        return false;
    }

    public static void main(String[] args) {
        int groupSize = 33;  // Number of people in the group
        int totalSimulations = 10000;  // Number of times we test this

        Birthday simulation = new Birthday(groupSize, totalSimulations);
        double probability = simulation.findProbability();

        System.out.println("Estimated probability of at least one shared birthday: " + probability);
    }
}