package poketcgproject.cards;

/**
 * Abstract class representing a card in PTCG
 */
public abstract class Card {
    protected String name;

    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}