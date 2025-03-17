package poketcgproject.cards.trainer;

import poketcgproject.PTCG;

public class RareCandy extends Trainer {
    public RareCandy(PTCG game) {
        super("Rare Candy", game);
    }

    @Override
    public void use() {
        // just need this here for montecarlo
        System.out.println("I am a rare candy");
    }
}
