package MonteCarlo;

import java.util.Random;

public class doorGame {
    private static final int NUM_SIMULATIONS = 10000;
    private static Random random = new Random();

    // Simulate a single round of the Monty Hall game
    private boolean playGame(boolean switchDoor) {
        Door[] doors = new Door[3];

        // Randomly assign a prize to one of the doors
        int prizeDoor = random.nextInt(3);
        for (int i = 0; i < 3; i++) {
            doors[i] = new Door(i == prizeDoor);
        }

        // Player picks a random door
        int playerChoice = random.nextInt(3);

        // Host reveals a door without a prize and not the player's choice
        int revealedDoor;
        do {
            revealedDoor = random.nextInt(3);
        } while (revealedDoor == playerChoice || doors[revealedDoor].hasPrize());

        // If player switches, they switch to the remaining unopened door
        if (switchDoor) {
            playerChoice = 3 - playerChoice - revealedDoor;  // The only remaining door
        }

        // Check if the player wins
        return doors[playerChoice].hasPrize();
    }

    // Run the simulation
    public double runSimulation(boolean switchDoor) {
        int wins = 0;
        for (int i = 0; i < NUM_SIMULATIONS; i++) {
            if (playGame(switchDoor)) {
                wins++;
            }
        }
        return (double) wins / NUM_SIMULATIONS * 100;
    }

    // Main method limited to 4 lines of code
    public static void main(String[] args) {
        doorGame game = new doorGame();
        System.out.printf("Win percentage without switching: %.2f%%\n", game.runSimulation(false));
        System.out.printf("Win percentage with switching: %.2f%%\n", game.runSimulation(true));
    }
}