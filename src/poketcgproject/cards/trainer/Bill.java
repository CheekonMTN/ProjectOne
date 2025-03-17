package poketcgproject.cards.trainer;

import poketcgproject.PTCG;

public class Bill extends Trainer {
    public Bill(PTCG game) {
        super("Bill", game);
    }

    @Override
    public void use() {
        System.out.println("Using Bill - Draw 2 cards!");
        game.drawCard(game.getCurrentPlayerState(), 2);
    }
}