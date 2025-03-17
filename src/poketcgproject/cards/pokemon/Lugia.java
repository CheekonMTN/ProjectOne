package poketcgproject.cards.pokemon;
/**
 * Lugia card
 * Base HP: 150
 * Attack: Elemental Blast (150 base damage)
 * Energy Cost: 3
 * Special Effect: After using attack, discard all 3 energy
 */
import poketcgproject.PTCG;
import poketcgproject.Player;
public class Lugia extends Pokemon {
    public Lugia(PTCG game) {
        super("Lugia", 150, "Elemental Blast", 150, 3, 2, game);
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        if (getEnergyAttached() < getEnergyCost()) {
            System.out.println("You need 3 energy on Lugia to use Elemental Blast!");
            return;
        }

        System.out.println("Lugia uses Elemental Blast!");
        System.out.println("Discarding 3 energy to deal 200 damage!");

        target.takeDamage(getAttackDamage());
        removeEnergy(3);

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}