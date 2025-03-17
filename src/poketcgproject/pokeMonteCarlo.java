package poketcgproject;


import poketcgproject.cards.Card;
import poketcgproject.cards.energy.Energy;
import poketcgproject.cards.pokemon.Electivire;
import poketcgproject.cards.trainer.RareCandy;

import java.util.Collections;

public class pokeMonteCarlo {


    // checks if all copies of Rare Candy are in the prize pile
    private boolean checkBrick(Player player, int rareCandyCount) {
        int rareCandyInPrize = 0;
        for (Card card : player.getPrize()) {
            if (card.getName().equals("Rare Candy")) {
                rareCandyInPrize++;
            }
        }
        return rareCandyInPrize == rareCandyCount;
    }

    // simulates the probability of "bricking" with different counts of Rare Candy in the deck
    public void simulateBrickOdds(int trials) {
        System.out.println("Rare Candy Count, Brick %");

        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            int bricks = 0;

            for (int t = 0; t < trials; t++) {
                Player testPlayer = new Player();
                // Initialize deck - you'll need to implement this based on your card classes
                initializeDeck(testPlayer, 10, 50 - rareCandyCount, rareCandyCount);

                // Draw initial hand and check for Pokemon
                do {
                    testPlayer.getHand().clear();
                    for (int i = 0; i < 7 && !testPlayer.getDeck().isEmpty(); i++) {
                        testPlayer.addCardToHand(testPlayer.getDeck().remove(0));
                    }
                } while (!testPlayer.hasPokemonInHand());

                testPlayer.setPrizeCards();

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

    private void initializeDeck(Player player, int pokemonCount, int energyCount, int rareCandyCount) {
        PTCG game = new PTCG();
        for (int i = 0; i < pokemonCount; i++) {
            player.getDeck().add(new Electivire(game));
            for (int e = 0; e < energyCount; e++) {
                player.getDeck().add(new Energy());
            }
            for (int r = 0; r < rareCandyCount; r++) {
                player.getDeck().add(new RareCandy(game));
            }
            Collections.shuffle(player.getDeck());
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