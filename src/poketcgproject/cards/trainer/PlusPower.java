package poketcgproject.cards.trainer;
/**
 * Trainer card: Plus Power
 * Effect: Add 20 damage to your Active Pokemon's attack
 * Restrictions:
 * - Can only be used if you have an Active Pokemon
 * - Damage boost lasts until next attack
 */
import poketcgproject.PTCG;
import poketcgproject.Player;
import poketcgproject.cards.pokemon.Pokemon;

public class PlusPower extends Trainer {

    public PlusPower(PTCG game) {
        super("Plus Power", game);
        this.game = game;
    }

    @Override
    public void use() {
        Player playerState = game.getCurrentPlayerState();
        Pokemon activePokemon = playerState.getActivePokemon();
        if (activePokemon == null) {
            System.out.println("No active Pokemon to use Plus Power on!");
            return;
        }
        activePokemon.addDamageBoost(20);
        System.out.println("Added +20 damage to " + activePokemon.getName() + "'s next attack!");
    }
}