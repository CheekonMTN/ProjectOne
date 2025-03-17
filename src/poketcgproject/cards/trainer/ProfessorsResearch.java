package poketcgproject.cards.trainer;

import poketcgproject.PTCG;
import poketcgproject.Player;

public class ProfessorsResearch extends Trainer {
    public ProfessorsResearch(PTCG game) {
        super("Professor's Research", game);
    }

    @Override
    public void use() {
        Player currentPlayer = game.getCurrentPlayerState();
        System.out.println("Using Professor's Research - Discard hand and draw 7 cards!");

        // Discard all cards in hand
        while (!currentPlayer.getHand().isEmpty()) {
            currentPlayer.addCardToDiscard(currentPlayer.getHand().remove(0));
        }

        // Draw 7 new cards
        game.drawCard(currentPlayer, 7);
    }
}