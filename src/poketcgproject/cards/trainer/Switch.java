package poketcgproject.cards.trainer;
/**
 * Trainer card: Switch
 * Effect: Switch your Active Pokemon with one
 * of your Benched Pokemon
 * Restrictions:
 * - Cannot be used if bench is empty
 * - Cannot be used if there is no active Pokemon
 */
import poketcgproject.PTCG;
import poketcgproject.Player;
import poketcgproject.cards.pokemon.Pokemon;
import java.util.Scanner;

public class Switch extends Trainer {
    public Switch(PTCG game) {
        super("Switch", game);
    }

    @Override
    public void use() {
        Player currentPlayer = game.getCurrentPlayerState();
        Pokemon active = currentPlayer.getActivePokemon();

        if (active == null) {
            System.out.println("No active Pokemon to switch!");
            return;
        }

        if (currentPlayer.getBench().isEmpty()) {
            System.out.println("No Pokemon on bench to switch with!");
            return;
        }

        System.out.println("Choose a Pokemon from bench to switch with (0 to cancel):");
        for (int i = 0; i < currentPlayer.getBench().size(); i++) {
            System.out.println((i + 1) + ". " + currentPlayer.getBench().get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < currentPlayer.getBench().size()) {
            Pokemon benchPokemon = currentPlayer.getBench().remove(choice);
            currentPlayer.getBench().add(active);
            currentPlayer.setActivePokemon(benchPokemon);
            System.out.println("Switched " + active.getName() + " with " + benchPokemon.getName());
        } else {
            System.out.println("Invalid choice!");
        }
    }
}