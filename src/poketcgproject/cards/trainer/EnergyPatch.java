package poketcgproject.cards.trainer;
/**
 * Trainer card: Energy Patch
 * Effect: Attach 1 energy to 1 of your Benched Pokemon
 * Input required:
 * - Player selects target Pokemon from bench
 * Restrictions:
 * - Cannot be used if bench is empty
 * - Can only target benched Pokemon
 */
import poketcgproject.PTCG;
import poketcgproject.Player;
import poketcgproject.cards.pokemon.Pokemon;
import java.util.Scanner;

public class EnergyPatch extends Trainer {
    private PTCG game;
    private Player playerState;

    public EnergyPatch(PTCG game) {
        super("Energy Patch", game);
        this.game = game;
        this.playerState = game.getCurrentPlayerState();
    }

    @Override
    public void use() {
        if (playerState.getBench().isEmpty()) {
            System.out.println("No Pokemon on bench to attach energy to!");
            return;
        }

        System.out.println("Choose a Pokemon from your bench to attach energy to:");
        for (int i = 0; i < playerState.getBench().size(); i++) {
            Pokemon benchPokemon = playerState.getBench().get(i);
            System.out.printf("%d. %s (Energy: %d)\n",
                    i + 1,
                    benchPokemon.getName(),
                    benchPokemon.getEnergyAttached());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < playerState.getBench().size()) {
            playerState.getBench().get(choice).addEnergy();
            System.out.println("Attached energy to " + playerState.getBench().get(choice).getName());
        } else {
            System.out.println("Invalid choice!");
        }
    }
}