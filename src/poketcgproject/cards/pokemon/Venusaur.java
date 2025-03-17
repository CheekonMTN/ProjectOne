package poketcgproject.cards.pokemon;
/**
 * Venusaur card : Samran's card
 * Base HP: 220
 * Attack: Solar Beam (120 base damage)
 * Energy Cost: 3
 * Special Effect: If opponent's active Pokemon has
 * 2 or more energy attached, deals 50 additional damage
 */
import poketcgproject.PTCG;
import poketcgproject.Player;

public class Venusaur extends Pokemon {
    public Venusaur(PTCG game) {
        super("Venusaur", 160, "Mega Drain", 100, 3, 2, game);
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        if (getEnergyAttached() < getEnergyCost()) {
            System.out.println("You need 3 energy on Venusaur to use Mega Drain!");
            return;
        }

        System.out.println("Venusaur uses Mega Drain!");
        target.takeDamage(getAttackDamage());
        heal(40);
        System.out.println("Venusaur stole 40 HP from " + target.getName() + "!");

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}