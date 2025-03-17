package poketcgproject.cards.trainer;

import poketcgproject.PTCG;
import poketcgproject.Player;
import java.util.Collections;

public class ProfOakNewTheory extends Trainer {
    public ProfOakNewTheory(PTCG game) {
        super("Professor Oak's New Theory", game);
    }

    @Override
    public void use() {
        Player currentPlayer = game.getCurrentPlayerState();

        // Put hand back into deck
        while (!currentPlayer.getHand().isEmpty()) {
            currentPlayer.getDeck().add(currentPlayer.getHand().remove(0));
        }

        // Shuffle deck
        Collections.shuffle(currentPlayer.getDeck());

        // Draw 6 cards
        System.out.println("Drawing 6 new cards!");
        game.drawCard(currentPlayer, 6);
    }
}