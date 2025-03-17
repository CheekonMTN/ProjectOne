package poketcgproject;

import poketcgproject.cards.Card;
import poketcgproject.cards.energy.Energy;
import poketcgproject.cards.pokemon.*;
import poketcgproject.cards.trainer.*;
import java.util.*;
import java.util.Random;

public class PTCG {
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private boolean isPlayer1Turn;
    private boolean hasAttacked;

    public PTCG() {
        player1 = new Player();
        player2 = new Player();
        scanner = new Scanner(System.in);
        isPlayer1Turn = true;
        hasAttacked = false;

    }
    // Initializes the game for both players, shuffling their decks and drawing initial hands
    public void initializeGame() {
        // Initialize decks with a balanced composition
        initializeDeck(player1, 21, 18, 21);
        initializeDeck(player2, 21, 18, 21);

        // Draw initial hands
        drawInitialHand(player1);
        drawInitialHand(player2);
    }
    // draws initial hand of 7 cards
    // keeps drawing until at least one pokemon is in hand
    private void drawInitialHand(Player player) {
        do {
            player.getHand().clear();
            drawCard(player, 7);
        } while (!player.hasPokemonInHand());
    }
    // gets current player state
    public Player getCurrentPlayerState() {
        return isPlayer1Turn ? player1 : player2;
    }
    // runs the game
    public void runGame() {
        initializeGame();
        boolean gameRunning = true;
        while (gameRunning) {
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            Player opponent = isPlayer1Turn ? player2 : player1;

            handleDeadActivePokemon(currentPlayer);
            removeSummoningSickness(currentPlayer);

            System.out.println("\n" + (isPlayer1Turn ? "Player 1's" : "Player 2's") + " turn!");
            drawCard(currentPlayer, 1);
            displayHand(currentPlayer);
            currentPlayer.setHasAttachedEnergy(false);
            hasAttacked = false;

            gameRunning = processTurn(currentPlayer, opponent);
        }
    }
    // handles knocked out active pokemon
    private void handleDeadActivePokemon(Player player) {
        if (player.getActivePokemon() == null && !player.getBench().isEmpty()) {
            System.out.println("\nYou must select a new active Pokemon from your bench!");
            moveToActive(player);
            if (player.getActivePokemon() == null) {
                System.out.println("You must have an active Pokemon to continue!");
            }
        }
    }
    // removes summoning sickness when player's pokemon
    // has been in play for at least one turn
    // (can't attack first turn pokemon played)
    private void removeSummoningSickness(Player player) {
        if (player.getActivePokemon() != null) {
            player.getActivePokemon().removeSummoningSickness();
        }
        for (Pokemon pokemon : player.getBench()) {
            pokemon.removeSummoningSickness();
        }
    }
    // processes the player's turn, actual logic of the turn
    private boolean processTurn(Player currentPlayer, Player opponent) {
        boolean turnEnded = false;
        while (!turnEnded) {
            displayTurnOptions(currentPlayer);
            System.out.print("> ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    playCard(currentPlayer);
                    break;
                case 2:
                    if (attack(currentPlayer, opponent)) {
                        turnEnded = true;
                        isPlayer1Turn = !isPlayer1Turn;
                    }
                    if (isGameOver(opponent)) {
                        System.out.println((isPlayer1Turn ? "Player 1" : "Player 2") + " wins!");
                        return false;
                    }
                    break;
                case 3:
                    retreatPokemon(currentPlayer);
                    break;
                case 4:
                    showGameState(currentPlayer, opponent);
                    break;
                case 5:
                    moveToActive(currentPlayer);
                    break;
                case 6:
                    turnEnded = true;
                    isPlayer1Turn = !isPlayer1Turn;
                    break;
                case 7:
                    return false;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        return true;
    }
    // shows options for player's turn
    private void displayTurnOptions(Player player) {
        System.out.println("\nChoose an action:");
        System.out.println("1. Play Card");
        System.out.println("2. Attack" + (hasAttacked ? " (Already used)" : ""));
        System.out.println("3. Retreat Active Pokemon" +
                (player.getActivePokemon() != null ?
                        String.format(" (Cost: %d, Current Energy: %d)",
                                player.getActivePokemon().getRetreatCost(),
                                player.getActivePokemon().getEnergyAttached()) :
                        " (No active Pokemon)"));
        System.out.println("4. View game state");
        System.out.println("5. Move Pokemon from bench to active");
        System.out.println("6. End Turn");
        System.out.println("7. Quit Game");
    }
    //initializes deck for both players,
    //both players have the same deck in this version
    private void initializeDeck(Player state, int pokemonCount, int energyCount, int trainerCount) {
        state.getDeck().clear();
        for (int i = 0; i < pokemonCount / 7; i++) {
            state.getDeck().add(new Electivire(this));
            state.getDeck().add(new Jigglypuff(this));
            state.getDeck().add(new Lugia(this));
            state.getDeck().add(new Lilligant(this));
            state.getDeck().add(new OgerponEX(this));
            state.getDeck().add(new Venusaur(this));
            state.getDeck().add(new Meowth(this));
        }
        for (int i = 0; i < energyCount; i++) {
            state.getDeck().add(new Energy());
        }
        for (int i = 0; i < trainerCount / 8; i++) {
            state.getDeck().add(new Bill(this));
            state.getDeck().add(new SuperRod(this));
            state.getDeck().add(new NestBall(this));
            state.getDeck().add(new Switch(this));
            state.getDeck().add(new EnergyPatch(this));
            state.getDeck().add(new ProfOakNewTheory(this));
            state.getDeck().add(new PlusPower(this));
            state.getDeck().add(new ProfessorsResearch(this));
        }
        Collections.shuffle(state.getDeck());
        state.setPrizeCards();
    }
    // draws a card from the deck
    public void drawCard(Player state, int count) {
        for (int i = 0; i < count && !state.getDeck().isEmpty(); i++) {
            Card drawn = state.getDeck().remove(0);
            state.addCardToHand(drawn);
        }
    }
    // checks if opponent's active pokemon is knocked out
    // and handles drawing a prize card if so
    private void handleKnockedOutPokemon(Player defender, Player attacker) {
        if (defender.getActivePokemon() != null && defender.getActivePokemon().getHp() <= 0) {
            System.out.println(defender.getActivePokemon().getName() + " was knocked out!");
            defender.addCardToDiscard(defender.getActivePokemon());
            defender.setActivePokemon(null);

            if (!attacker.getPrize().isEmpty()) {
                drawPrizeCard(attacker);
            }
        }
    }
    // draws a prize card when opponent active pokemon knocked out
    private void drawPrizeCard(Player player) {
        Random random = new Random();
        Card prizeCard = player.getPrize().remove(random.nextInt(player.getPrize().size()));
        player.addCardToHand(prizeCard);
        System.out.println("Got " + prizeCard.getName() + " from prizes!");
        System.out.println("Prizes remaining: " + player.getPrize().size());
    }
    // shows all cards in the player's hand
    private void displayHand(Player player) {
        System.out.println("\nYour hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, getCardDescription(hand.get(i)));
        }
    }
    //gives description of card
    private String getCardDescription(Card card) {
        if (card instanceof Pokemon p) {
            return String.format("%s (HP: %d, Energy: %d)", p.getName(), p.getHp(), p.getEnergyAttached());
        } else if (card instanceof Energy) {
            return "Energy";
        } else if (card instanceof Trainer t) {
            return "Trainer - " + t.getName();
        }
        return card.toString();
    }
    // handles playing a card from your hand
    private void playCard(Player player) {
        List<Card> hand = player.getHand();
        if (hand.isEmpty()) {
            System.out.println("No cards in hand!");
            return;
        }

        System.out.println("Choose a card to play (0 to cancel):");
        displayHand(player);

        int choice = scanner.nextInt() - 1;
        if (choice < 0 || choice >= hand.size()) {
            return;
        }

        Card card = hand.get(choice);
        // Only Pokemon cards need player state set
        if (card instanceof Pokemon) {
            ((Pokemon) card).setPlayerState(player);
        }

        if (card instanceof Pokemon) {
            playPokemon(player, (Pokemon) card);
        } else if (card instanceof Energy) {
            playEnergy(player, (Energy) card);
        } else if (card instanceof Trainer) {
            playTrainer(player, (Trainer) card);
        }
    }
    //handles playing pokemon,
    // lets you put on active (if not there) or bench
    private void playPokemon(Player player, Pokemon pokemon) {
        if (player.getActivePokemon() == null) {
            player.setActivePokemon(pokemon);
            player.removeCardFromHand(pokemon);
            System.out.println("Played " + pokemon.getName() + " as active Pokemon!");
        } else if (player.getBench().size() < 5) {
            player.addCardToBench(pokemon);
            player.removeCardFromHand(pokemon);
            System.out.println("Played " + pokemon.getName() + " to bench!");
        } else {
            System.out.println("Bench is full!");
        }
    }
    // handles playing an energy,
    // allows you to put on active or bench pokemon
    private void playEnergy(Player player, Energy energy) {
        if (player.hasAttachedEnergy()) {
            System.out.println("Already attached an energy this turn!");
            return;
        }
        System.out.println("Attach energy to:");
        System.out.println("1. Active Pokemon" +
                (player.getActivePokemon() != null ?
                        String.format(" (%s - Energy: %d)",
                                player.getActivePokemon().getName(),
                                player.getActivePokemon().getEnergyAttached()) :
                        " (None)"));
        System.out.println("2. Bench Pokemon");
        int target = scanner.nextInt();

        if (target == 1 && player.getActivePokemon() != null) {
            player.getActivePokemon().addEnergy();
        } else if (target == 2 && !player.getBench().isEmpty()) {
            System.out.println("Choose a Pokemon from your bench:");
            for (int i = 0; i < player.getBench().size(); i++) {
                Pokemon benchPokemon = player.getBench().get(i);
                System.out.printf("%d. %s (Energy: %d)\n",
                        i + 1,
                        benchPokemon.getName(),
                        benchPokemon.getEnergyAttached());
            }
            int benchChoice = scanner.nextInt() - 1;
            if (benchChoice >= 0 && benchChoice < player.getBench().size()) {
                player.getBench().get(benchChoice).addEnergy();
            }
        }
        player.removeCardFromHand(energy);
        player.setHasAttachedEnergy(true);
        System.out.println("Attached energy!");
    }
    // handles playing a trainer
    private void playTrainer(Player player, Trainer trainer) {
        trainer.use();
        player.removeCardFromHand(trainer);
        player.addCardToDiscard(trainer);
    }
    // retreats active pokemon for one on bench
    // must have enough energy to retreat
    private void retreatPokemon(Player player) {
        if (player.getActivePokemon() == null) {
            System.out.println("No active Pokemon to retreat!");
            return;
        }
        if (player.getBench().isEmpty()) {
            System.out.println("No Pokemon on bench to retreat to!");
            return;
        }
        Pokemon active = player.getActivePokemon();
        if (active.getEnergyAttached() < active.getRetreatCost()) {
            System.out.println("Not enough energy to retreat!");
            return;
        }
        System.out.println("Choose a Pokemon to retreat to:");
        for (int i = 0; i < player.getBench().size(); i++) {
            System.out.println((i + 1) + ". " + getCardDescription(player.getBench().get(i)));
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < player.getBench().size()) {
            Pokemon newActive = player.getBench().remove(choice);
            player.getBench().add(active);
            player.setActivePokemon(newActive);
            active.removeEnergy(active.getRetreatCost());
            System.out.println("Retreated " + active.getName() + " for " + newActive.getName());
        }
    }
    //moves pokemon from bench to active
    public Pokemon moveToActive(Player player) {
        if (player.getActivePokemon() != null) {
            System.out.println("Already have an active Pokemon!");
            return null;
        }

        List<Pokemon> bench = player.getBench();
        if (bench.isEmpty()) {
            System.out.println("No Pokemon on bench!");
            return null;
        }

        System.out.println("Choose a Pokemon from bench (0 to cancel):");
        for (int i = 0; i < bench.size(); i++) {
            System.out.println((i + 1) + ". " + bench.get(i).getName());
        }

        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < bench.size()) {
            player.setActivePokemon(bench.remove(choice));
        }
        return null;
    }
    //executes attack between both active pokemon
    private boolean attack(Player attacker, Player defender) {
        if (attacker.getActivePokemon() == null) {
            System.out.println("No Active Pokemon to attack with!");
            return false;
        }

        if (attacker.getActivePokemon().hasSummoningSickness()) {
            System.out.println("This pokemon can't attack yet due to summoning sickness!");
            return false;
        }

        Pokemon attackingPokemon = attacker.getActivePokemon();
        Pokemon targetPokemon = defender.getActivePokemon();

        if (defender.getActivePokemon() == null && defender.getBench().isEmpty()) {
            System.out.println("Cannot attack - opponent has no Pokemon in play!");
            return false;
        }

        if (targetPokemon == null) {
            moveToActive(defender);
            targetPokemon = defender.getActivePokemon();
        }

        if (targetPokemon != null) {
            attackingPokemon.attack(targetPokemon);
            if (targetPokemon.knockedOut()) {
                defender.setActivePokemon(null);
            }
        }

        handleKnockedOutPokemon(defender, attacker);
        return true;
    }
    // shows both players game state
    private void showGameState(Player currentPlayer, Player opponent) {
        System.out.println("\n=== GAME STATE ===");
        System.out.println("Your active Pokemon: " +
                (currentPlayer.getActivePokemon() != null ? getCardDescription(currentPlayer.getActivePokemon()) : "None"));
        System.out.println("Your bench:");
        currentPlayer.getBench().forEach(p ->
                System.out.println("- " + getCardDescription(p)));
        System.out.println("Opponent's active Pokemon: " +
                (opponent.getActivePokemon() != null ? getCardDescription(opponent.getActivePokemon()) : "None"));
        System.out.println("Opponent's bench size: " + opponent.getBench().size());
        System.out.println("Your prizes remaining: " + currentPlayer.getPrize().size());
    }
    //checks if game over
    private boolean isGameOver(Player player) {
        Player currentPlayer = isPlayer1Turn ? player1 : player2;

        // check if a player has no Pokemon in play
        if (player.getActivePokemon() == null && player.getBench().isEmpty()) {
            System.out.println((isPlayer1Turn ? "Player 1" : "Player 2") + " wins - opponent has no Pokemon in play!");
            return true;
        }

        // check if a player has taken all prize cards
        if (currentPlayer.getPrize().isEmpty()) {
            System.out.println((isPlayer1Turn ? "Player 1" : "Player 2") + " wins - all prize cards collected!");
            return true;
        }

        // check if a player cannot draw a card at the start of their turn
        if (player.getDeck().isEmpty()) {
            System.out.println((isPlayer1Turn ? "Player 2" : "Player 1") + " wins - opponent cannot draw a card!");
            return true;
        }

        return false;
    }
}