/*package poketcgproject;

import poketcgproject.cards.Card;
import poketcgproject.cards.energy.Energy;
import poketcgproject.cards.pokemon.*;
import poketcgproject.cards.trainer.*;

import java.util.*;

public class PokeTCG {
    List<Card> deck = new ArrayList<>();
    List<Card> hand = new ArrayList<>();
    List<Card> discard = new ArrayList<>();
    List<Card> prize = new ArrayList<>();
    Pokemon activePokemon = null;
    List<Pokemon> bench = new ArrayList<>();
    Random random = new Random();
    private boolean hasAttachedEnergy = false;
    static Scanner scanner = new Scanner(System.in);

    String getCardDescription(Card card) {
        if (card instanceof Pokemon) {
            Pokemon p = (Pokemon) card;
            return String.format("%s (HP: %d, Attack: %s for %d damage)",
                    p.getName(), p.getHp(), p.getAttackName(), p.getAttackDamage());
        } else if (card instanceof Energy) {
            return "Energy";
        } else if (card instanceof Trainer) {
            return "Trainer - " + ((Trainer) card).getName();
        }
        return card.toString();
    }

    void displayHand() {
        if (hand.isEmpty()) {
            System.out.println("Your hand is empty!");
            return;
        }

        System.out.println("\nYour hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, getCardDescription(hand.get(i)));
        }
        System.out.println();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    void playCard() {
        if (hand.isEmpty()) {
            System.out.println("Your hand is empty!");
            return;
        }

        System.out.println("\nChoose a card to play:");
        displayHand();

        System.out.print("> ");
        int choice = scanner.nextInt() - 1;  // Subtract 1 because display is 1-based

        if (choice < 0 || choice >= hand.size()) {
            System.out.println("Invalid choice!");
            return;
        }

        Card selectedCard = hand.get(choice);

        if (selectedCard instanceof Pokemon) {
            if (activePokemon == null) {
                activePokemon = (Pokemon) selectedCard;
                System.out.println("Played " + activePokemon.getName() + " as Active Pokémon!");
            } else if (bench.size() < 5) {
                bench.add((Pokemon) selectedCard);
                System.out.println("Played " + ((Pokemon) selectedCard).getName() + " on the Bench!");
            } else {
                System.out.println("Bench is full! Can't play more Pokémon!");
                return;
            }
        } else if (selectedCard instanceof Energy) {
            if (hasAttachedEnergy) {
                System.out.println("You can only attach one energy per turn!");
                return;
            }
            System.out.println("\nWhere would you like to attach the energy?");
            System.out.println("1. Active Pokemon" + (activePokemon != null ? " (" + activePokemon.getName() + ")" : " (None)"));
            System.out.println("2. Bench Pokemon");

            System.out.print(">");
            int target = scanner.nextInt();

            if (target == 1) {
                // Original active Pokemon energy attachment logic
                if (activePokemon == null) {
                    System.out.println("No active Pokémon to attach energy to!");
                    return;
                }
                activePokemon.addEnergy();
                hasAttachedEnergy = true;
                System.out.println("Attached Energy to " + activePokemon.getName());
                System.out.println("Total Energy: " + activePokemon.getEnergyAttached());
            } else if (target == 2) {
                // Added: Bench Pokemon energy attachment logic
                if (bench.isEmpty()) {
                    System.out.println("No Pokemon on bench to attach energy to!");
                    return;
                }
                // Added: Display bench Pokemon with their current energy
                System.out.println("\nChoose a Pokemon from your bench:");
                for (int i = 0; i < bench.size(); i++) {
                    Pokemon benchPokemon = bench.get(i);
                    System.out.printf("%d. %s (Current Energy: %d)\n",
                            i + 1, benchPokemon.getName(), benchPokemon.getEnergyAttached());
                }
                System.out.print("> ");
                int benchChoice = scanner.nextInt() - 1;

                // Added: Handle bench Pokemon energy attachment
                if (benchChoice >= 0 && benchChoice < bench.size()) {
                    bench.get(benchChoice).addEnergy();
                    hasAttachedEnergy = true;
                    System.out.println("Attached Energy to " + bench.get(benchChoice).getName());
                    System.out.println("Total Energy: " + bench.get(benchChoice).getEnergyAttached());
                } else {
                    System.out.println("Invalid choice!");
                    return;
                }
            } else {
                System.out.println("Invalid choice!");
                return;
            }
            discard.add(selectedCard);
        } else if (selectedCard instanceof Trainer) {
            System.out.println("Played Trainer card: " + ((Trainer) selectedCard).getName());
            ((Trainer) selectedCard).use();
            discard.add(selectedCard);

        }

        hand.remove(choice);
    }

    void initializeDeck(int pokemonCount, int energyCount, int trainerCount) {
        deck.clear();
        for (int i = 0; i < pokemonCount / 7; i++) {
            deck.add(new Electivire(this));  // Add this parameter
            deck.add(new Jigglypuff(this));
            deck.add(new Lugia(this));       // Add this parameter
            deck.add(new Lilligant(this));
            deck.add(new OgerponEX(this));   // Add this parameter
            deck.add(new Venusaur(this));    // Add this parameter
            deck.add(new Meowth(this));      // Add this parameter
        }
        for (int i = 0; i < energyCount; i++) {
            deck.add(new Energy());
        }
        for (int i = 0; i < trainerCount / 5; i++) {
            deck.add(new Bill(this));
            deck.add(new ProfessorsResearch(this));
            deck.add(new SuperRod(this));
            deck.add(new NestBall(this));
            deck.add(new Switch(this));
        }
        Collections.shuffle(deck);
    }
    void drawCard(int count) {
        for (int i = 0; i < count && !deck.isEmpty(); i++) {
            Card drawn = deck.remove(0);
            hand.add(drawn);
            System.out.println("Drew: " + getCardDescription(drawn));
        }
    }

    void setPrizeCards() {
        prize.clear();
        for (int i = 0; i < 3; i++) {
            prize.add(deck.remove(0));
        }
    }
    void showGameState(PokeTCG opponent) {
        // Show current player's active Pokemon with detailed stats
        System.out.println("\nGame State:");
        System.out.println("Your active Pokemon: " +
                (activePokemon != null ? activePokemon.getName() +
                        " (HP: " + activePokemon.getHp() +
                        ", Energy: " + activePokemon.getEnergyAttached() + ")" : "None"));
        // Show current player's bench Pokemon
        System.out.println("Your bench Pokemon:");
        if (bench.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Pokemon pokemon : bench) {
                System.out.println("- " + pokemon.getName() +
                        " (HP: " + pokemon.getHp() +
                        ", Energy: " + pokemon.getEnergyAttached() + ")");
            }
        }
        // Show current player's game statistics
        System.out.println("Cards in hand: " + hand.size());
        System.out.println("Cards in discard pile: " + discard.size());
        System.out.println("Prize cards remaining: " + prize.size());
        // Show opponent's active Pokemon with detailed stats
        System.out.println("\nOpponent's active Pokemon: " +
                (opponent.activePokemon != null ? opponent.activePokemon.getName() +
                        " (HP: " + opponent.activePokemon.getHp() +
                        ", Energy: " + opponent.activePokemon.getEnergyAttached() + ")" : "None"));
        // Show opponent's bench Pokemon
        System.out.println("Opponent's bench Pokemon:");
        if (opponent.bench.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Pokemon pokemon : opponent.bench) {
                System.out.println("- " + pokemon.getName() +
                        " (HP: " + pokemon.getHp() +
                        ", Energy: " + pokemon.getEnergyAttached() + ")");
            }
        }
    }

    boolean hasPokemonInHand() {
        for (Card card : hand) {
            if (card instanceof Pokemon) return true;
        }
        return false;
    }

    void moveToActive() {
        if (activePokemon != null) {
            System.out.println("You already have an active Pokemon!");
            return;
        }

        if (bench.isEmpty()) {
            System.out.println("No Pokemon on the bench to move!");
            return;
        }

        System.out.println("\nChoose a Pokemon from your bench to make active:");
        for (int i = 0; i < bench.size(); i++) {
            Pokemon benchPokemon = bench.get(i);
            System.out.printf("%d. %s (HP: %d, Energy: %d)\n",
                    i + 1,
                    benchPokemon.getName(),
                    benchPokemon.getHp(),
                    benchPokemon.getEnergyAttached());
        }

        System.out.print("> ");
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < bench.size()) {
            activePokemon = bench.remove(choice);
            System.out.println(activePokemon.getName() + " is now your active Pokemon!");
        } else {
            System.out.println("Invalid choice!");
        }
    }

    void retreatPokemon(Scanner scanner) {
        if (activePokemon == null) {
            System.out.println("No active Pokemon to retreat!");
            return;
        }

        if (bench.isEmpty()) {
            System.out.println("No Pokemon on the Bench to retreat to!");
            return;
        }

        int retreatCost = activePokemon.getRetreatCost();
        if (activePokemon.getEnergyAttached() < retreatCost) {
            System.out.println("Not enough energy attached! Need " + retreatCost + " energy to retreat.");
            return;
        }

        System.out.println("\nChoose a Pokemon to retreat to:");
        for (int i = 0; i < bench.size(); i++) {
            Pokemon benchPokemon = bench.get(i);
            System.out.printf("%d. %s (HP: %d)\n", i + 1, benchPokemon.getName(), benchPokemon.getHp());
        }

        System.out.print("> ");
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < bench.size()) {
            // Remove retreat cost energy cards
            for (int i = 0; i < retreatCost; i++) {
                activePokemon.removeEnergy(retreatCost);
                discard.add(new Energy());
            }

            // Swap Pokemon
            Pokemon temp = activePokemon;
            activePokemon = bench.remove(choice);
            bench.add(temp);
            System.out.println("Retreated " + temp.getName() + " to bench!");
            System.out.println(activePokemon.getName() + " is now active!");
        } else {
            System.out.println("Invalid choice!");
        }
    }

    void attack(PokeTCG opponent) {
        if (activePokemon == null) {
            System.out.println("No Active Pokémon to attack with!");
            return;
        }

        if (activePokemon.hasSummoningSickness()){
            System.out.println("This pokemon can't attack yet due to summoning sickness!");
            return;
        }

        if (opponent.activePokemon == null) {
            System.out.println("Opponent has no Pokémon. You win!");
            return;
        }

        activePokemon.attack(opponent.activePokemon);

        if (opponent.activePokemon.getHp() <= 0) {
            System.out.println(opponent.activePokemon.getName() + " was knocked out!");
            opponent.discard.add(opponent.activePokemon);
            opponent.activePokemon = null;

            if (!prize.isEmpty()) {
                Card prizeCard = prize.remove(random.nextInt(prize.size()));
                hand.add(prizeCard);
                System.out.println("Got " + getCardDescription(prizeCard) + " from prizes!");
                System.out.println("Prizes remaining: " + prize.size());
            }
        }
    }

    public static void runGame() {
        PokeTCG player1 = new PokeTCG();
        PokeTCG player2 = new PokeTCG();
        boolean hasAttacked = false;
        boolean isPlayer1Turn = true;

        // Initialize decks
        player1.initializeDeck(21, 19, 20);
        player2.initializeDeck(21, 19, 20);
        player1.setPrizeCards();
        player2.setPrizeCards();

        // Setup initial hands
        do {
            player1.hand.clear();
            player1.drawCard(7);
        } while (!player1.hasPokemonInHand());

        do {
            player2.hand.clear();
            player2.drawCard(7);
        }
        while (!player2.hasPokemonInHand());

        boolean gameRunning = true;
        while (gameRunning) {
            PokeTCG currentPlayer = isPlayer1Turn ? player1 : player2;
            PokeTCG opponent = isPlayer1Turn ? player2 : player1;

            if(currentPlayer.activePokemon == null && !currentPlayer.bench.isEmpty()){
                System.out.println("\nYou must select a new active Pokemon from your bench!");
                currentPlayer.moveToActive();
                if (currentPlayer.activePokemon == null){
                    System.out.println("You must have an active Pokemon to continue!");
                    continue;
                }
            }
            if (currentPlayer.activePokemon != null) {
                currentPlayer.activePokemon.removeSummoningSickness();
            }
            for (Pokemon pokemon : currentPlayer.bench) {
                pokemon.removeSummoningSickness();
            }

            System.out.println("\n" + (isPlayer1Turn ? "Player 1's" : "Player 2's") + " turn!");
            currentPlayer.drawCard(1);
            currentPlayer.displayHand();
            currentPlayer.hasAttachedEnergy = false;
            hasAttacked = false;

            boolean turnEnded = false;
            while (!turnEnded && gameRunning) {
                System.out.println("\nChoose an action:");
                System.out.println("1. Play Card");
                System.out.println("2. Attack" + (hasAttacked ? " (Already used)" : ""));
                System.out.println("3. Retreat Active Pokemon" +
                        (currentPlayer.activePokemon != null ?
                                String.format(" (Cost: %d, Current Energy: %d)",
                                        currentPlayer.activePokemon.getRetreatCost(),
                                        currentPlayer.activePokemon.getEnergyAttached()) :
                                " (No active Pokemon)"));
                System.out.println("4. View game state");
                System.out.println("5. Move Pokemon from bench to active");
                System.out.println("6. End Turn");
                System.out.println("7. Quit Game");

                System.out.print("> ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        currentPlayer.playCard();
                        break;
                    case 2:
                        currentPlayer.attack(opponent);
                        if (opponent.activePokemon == null && opponent.bench.isEmpty()) {
                            System.out.println((isPlayer1Turn ? "Player 1" : "Player 2") + " wins!");
                            gameRunning = false;
                        } else {
                            System.out.println("Turn ended after attack.");
                            turnEnded = true;
                            isPlayer1Turn = !isPlayer1Turn;
                        }
                        break;
                    case 3:
                        currentPlayer.retreatPokemon(scanner);
                        break;
                    case 4:
                       currentPlayer.showGameState(opponent);
                            break;
                    case 5:
                        currentPlayer.moveToActive();  // New case
                        break;
                    case 6:
                        turnEnded = true;
                        isPlayer1Turn = !isPlayer1Turn;
                        break;
                    case 7:
                        gameRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            runGame();
            System.out.println("\nWould you like to play another time? type y if yes, type n if no.");
            String answer = scanner.next().toLowerCase();
            playAgain = answer.equals("y");
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
*/

