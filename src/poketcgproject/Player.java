package poketcgproject;

import poketcgproject.cards.Card;
import poketcgproject.cards.pokemon.Pokemon;
import java.util.*;

public class Player {
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> discard;
    private List<Card> prize;
    private Pokemon activePokemon;
    private List<Pokemon> bench;
    private boolean hasAttachedEnergy;

    public Player() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        discard = new ArrayList<>();
        prize = new ArrayList<>();
        bench = new ArrayList<>();
        hasAttachedEnergy = false;
    }

    // Getters and Setters
    public List<Card> getDeck() { return deck; }
    public List<Card> getHand() { return hand; }
    public List<Card> getDiscard() { return discard; }
    public List<Card> getPrize() { return prize; }
    public Pokemon getActivePokemon() { return activePokemon; }
    public List<Pokemon> getBench() { return bench; }
    public boolean hasAttachedEnergy() { return hasAttachedEnergy; }

    public void setActivePokemon(Pokemon pokemon) {
        this.activePokemon = pokemon;
    }

    public void setHasAttachedEnergy(boolean value) {
        this.hasAttachedEnergy = value;
    }

    // Helper methods
    public boolean hasPokemonInHand() {
        return hand.stream().anyMatch(card -> card instanceof Pokemon);
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    public void addCardToDiscard(Card card) {
        discard.add(card);
    }

    public void addCardToBench(Pokemon pokemon) {
        bench.add(pokemon);
    }

    public Pokemon removeFromBench(int index) {
        return bench.remove(index);
    }
    public void setPrizeCards() {
        for (int i = 0; i < 1; i++) {
            if (!deck.isEmpty()) {
                prize.add(deck.remove(0));
            }
        }
    }
}