package poketcgproject.cards.trainer;

import poketcgproject.PTCG;
import poketcgproject.cards.Card;

public abstract class Trainer extends Card {
    protected PTCG game;

    public Trainer(String name, PTCG game) {
        super(name);
        this.game = game;
    }

    public abstract void use();
}