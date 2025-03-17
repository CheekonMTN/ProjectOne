package poketcgproject.cards.trainer;

import poketcgproject.PTCG;
import poketcgproject.Player;
import poketcgproject.cards.Card;
import poketcgproject.cards.pokemon.Pokemon;
import poketcgproject.cards.energy.Energy;
import java.util.Scanner;

public class SuperRod extends Trainer {
    private PTCG game;
    private Player playerState;

    public SuperRod(PTCG game) {
        super("Super Rod", game);
        this.game = game;
        this.playerState = game.getCurrentPlayerState();
    }

    @Override
    public void use() {
        System.out.println("Using Super Rod - Choose up to 3 Pokemon or Energy cards from discard pile to shuffle into deck!");

        // Check if there are any Pokemon or Energy cards in discard
        boolean canUseSuperRod = false;
        for (Card card : playerState.getDiscard()) {
            if (card instanceof Pokemon || card instanceof Energy) {
                canUseSuperRod = true;
                break;
            }
        }

        if (!canUseSuperRod) {
            System.out.println("No Pokemon or Energy cards in discard pile!");
            return;
        }

        for (int i = 0; i < Math.min(3, playerState.getDiscard().size()); i++) {
            System.out.println("\nValid cards in discard pile:");
            int validIndex = 0;
            for (int j = 0; j < playerState.getDiscard().size(); j++) {
                Card card = playerState.getDiscard().get(j);
                if (card instanceof Pokemon || card instanceof Energy) {
                    System.out.println(j + ". " + card.getName());
                    validIndex++;
                }
            }

            if (validIndex == 0) {
                System.out.println("No more Pokemon or Energy cards to recover!");
                break;
            }

            System.out.println("\nChoose a card to recover (-1 to stop): ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice == -1 || choice >= playerState.getDiscard().size()) {
                break;
            }

            Card selectedCard = playerState.getDiscard().get(choice);
            if (selectedCard instanceof Pokemon || selectedCard instanceof Energy) {
                playerState.getDeck().add(playerState.getDiscard().remove(choice));
                System.out.println("Card added to deck!");
            } else {
                System.out.println("Invalid choice! Must select a Pokemon or Energy card.");
                i--; // Don't count this as one of the 3 choices
            }
        }
    }
}