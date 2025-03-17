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
    // track if energy was attached this turn
    public void setHasAttachedEnergy(boolean value) {
        this.hasAttachedEnergy = value;
    }

    // Helper methods

    //check if hand has pokemon
    public boolean hasPokemonInHand() {
        return hand.stream().anyMatch(card -> card instanceof Pokemon);
    }
    // add card to hand
    public void addCardToHand(Card card) {
        hand.add(card);
    }
    // remove card from hand
    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }
    // put card in discard pile
    public void addCardToDiscard(Card card) {
        discard.add(card);
    }
    // add pokemon to bench
    public void addCardToBench(Pokemon pokemon) {
        bench.add(pokemon);
    }

    // take 6 cards from deck for prizes
    public void setPrizeCards() {
        for (int i = 0; i < 6; i++) {
            if (!deck.isEmpty()) {
                prize.add(deck.remove(0));
            }
        }
    }
}