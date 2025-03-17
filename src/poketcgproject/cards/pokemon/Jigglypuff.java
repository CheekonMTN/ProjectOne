package poketcgproject.cards.pokemon;
/**
 * Jigglypuff card : Samboni's card
 * Base HP: 80
 * Attack: Let's rollout! (150 base damage)
 * Energy Cost: 3
 * Special Effect: For each additional Pokemon with Let's rollout!
 * on your bench, multiply damage by number of Pokemon
 */
import poketcgproject.PTCG;
import poketcgproject.Player;

public class Jigglypuff extends Pokemon {
    private PTCG game;

    public Jigglypuff(PTCG game) {
        super("Jigglypuff", 80, "Let's rollout!", 150, 3, 2, game);
        this.game = game;
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        if (getEnergyAttached() < getEnergyCost()) {
            System.out.println("You need 3 energy on Jigglypuff to use Let's rollout!");
            return;
        }

        Player currentState = game.getCurrentPlayerState();
        int rolloutCount = 1; // Count itself
        for (Pokemon benchPokemon : currentState.getBench()) {
            if ("Let's rollout!".equals(benchPokemon.getAttackName())) {
                rolloutCount++;
            }
        }

        int baseDamage = getAttackDamage() * rolloutCount;
        int totalDamage = baseDamage + getDamageBoost();
        System.out.println("Jigglypuff uses Let's rollout!");
        System.out.println("Found " + (rolloutCount - 1) + " additional Pokemon with Let's rollout!");
        System.out.println("Dealing " + totalDamage + " damage!");
        target.takeDamage(totalDamage);
        resetDamageBoost();

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}