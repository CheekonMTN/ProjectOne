package poketcgproject.cards.trainer;

import poketcgproject.cards.Card;
import poketcgproject.cards.pokemon.Pokemon;
import poketcgproject.PTCG;
import poketcgproject.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class NestBall extends Trainer {
    private PTCG game;
    private Player playerState;

    public NestBall(PTCG game) {
        super("Nest Ball", game);
        this.game = game;
        this.playerState = game.getCurrentPlayerState();
    }

    @Override
    public void use() {
        if (playerState.getBench().size() >= 5) {
            System.out.println("Your bench is full!");
            return;
        }

        List<Pokemon> pokemonInDeck = new ArrayList<>();
        for (int i = 0; i < playerState.getDeck().size(); i++) {
            Card card = playerState.getDeck().get(i);
            if (card instanceof Pokemon) {
                pokemonInDeck.add((Pokemon) card);
            }
        }

        if (pokemonInDeck.isEmpty()) {
            System.out.println("No Pokemon found in deck!");
            return;
        }

        System.out.println("\nChoose a Pokemon to put on your bench:");
        for (int i = 0; i < pokemonInDeck.size(); i++) {
            Pokemon pokemon = pokemonInDeck.get(i);
            System.out.printf("%d. %s (HP: %d)\n", i + 1, pokemon.getName(), pokemon.getHp());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < pokemonInDeck.size()) {
            Pokemon chosen = pokemonInDeck.get(choice);
            playerState.getDeck().remove(chosen);
            playerState.getBench().add(chosen);
            System.out.println("Added " + chosen.getName() + " to your bench!");
            Collections.shuffle(playerState.getDeck());
        } else {
            System.out.println("Invalid choice!");
        }
    }
}