package poketcgproject;


import poketcgproject.cards.Card;
import poketcgproject.cards.energy.Energy;
import poketcgproject.cards.pokemon.Electivire;
import poketcgproject.cards.trainer.RareCandy;

import java.util.Collections;

public class pokeMonteCarlo {


    private boolean checkBrick(Player player, int rareCandyCount) {
        // Count Rare Candy cards in prizes
        int rareCandyInPrize = 0;
        for (Card card : player.getPrize()) {
            if (card instanceof RareCandy) {
                rareCandyInPrize++;
            }
        }
        return rareCandyInPrize == rareCandyCount;
    }

    private void initializeDeck(Player player, int pokemonCount, int energyCount, int rareCandyCount) {
        PTCG game = new PTCG();
        // Add Pokemon
        for (int i = 0; i < pokemonCount; i++) {
            player.getDeck().add(new Electivire(game));
        }
        // Add Energy
        for (int e = 0; e < energyCount; e++) {
            player.getDeck().add(new Energy());
        }
        // Add Rare Candy
        for (int r = 0; r < rareCandyCount; r++) {
            player.getDeck().add(new RareCandy(game));
        }
        Collections.shuffle(player.getDeck());
    }

    public void simulateBrickOdds(int trials) {
        System.out.println("Rare Candy Count, Brick %");
        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            int bricks = 0;
            for (int t = 0; t < trials; t++) {
                Player testPlayer = new Player();
                initializeDeck(testPlayer, 10, 50 - rareCandyCount, rareCandyCount);
                Collections.shuffle(testPlayer.getDeck());

                // Draw initial hand until Pokemon found
                do {
                    testPlayer.getHand().clear();
                    Collections.shuffle(testPlayer.getDeck());
                    for (int i = 0; i < 7 && !testPlayer.getDeck().isEmpty(); i++) {
                        testPlayer.addCardToHand(testPlayer.getDeck().remove(0));
                    }
                } while (!testPlayer.hasPokemonInHand());

                // Set prize cards
                for (int i = 0; i < 6 && !testPlayer.getDeck().isEmpty(); i++) {
                    testPlayer.getPrize().add(testPlayer.getDeck().remove(0));
                }

                if (checkBrick(testPlayer, rareCandyCount)) {
                    bricks++;
                }
            }
            double brickRate = (bricks / (double) trials) * 100;
            System.out.printf("%d, %.2f%%\n", rareCandyCount, brickRate);
        }
    }

    // Simulates how often a player will "mulligan" (redraw hand due to no Pokémon)
    public void simulateMulliganOdds(int trials) {
        System.out.println("Pokemon Count, Mulligan %");

        for (int pokemonCount = 1; pokemonCount <= 59; pokemonCount++) {
            int mulligans = 0;

            for (int t = 0; t < trials; t++) {
                Player testPlayer = new Player();
                initializeDeck(testPlayer, pokemonCount, 60 - pokemonCount, 0);

                for (int i = 0; i < 7 && !testPlayer.getDeck().isEmpty(); i++) {
                    testPlayer.addCardToHand(testPlayer.getDeck().remove(0));
                }

                if (!testPlayer.hasPokemonInHand()) {
                    mulligans++;
                }
            }

            double mulliganRate = (mulligans / (double) trials) * 100;
            System.out.printf("%d, %.2f%%\n", pokemonCount, mulliganRate);
        }
    }


    public static void runSimulations(int trials) {
        pokeMonteCarlo simulator = new pokeMonteCarlo();
        System.out.println("Running Brick Odds Simulation...");
        simulator.simulateBrickOdds(trials);
        System.out.println("\nRunning Mulligan Odds Simulation...");
        simulator.simulateMulliganOdds(trials);
    }
}