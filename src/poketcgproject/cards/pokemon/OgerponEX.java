package poketcgproject.cards.pokemon;
/**
 * Teal Mask Ogerpon EX card : Luke's card
 * Base HP: 220
 * Attack: Myriad Leaf Shower (30 base damage)
 * Energy Cost: 2
 * Special Effect: Add 20 damage for each energy card
 * attached to both active Pokemon
 */
import poketcgproject.PTCG;
import poketcgproject.Player;
public class OgerponEX extends Pokemon {
    public OgerponEX(PTCG game) {
        super("Teal Mask Ogerpon EX", 220, "Myriad Leaf Shower", 30, 2, 3, game);
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        if (this.getEnergyAttached() < getEnergyCost()) {
            System.out.println("You need at least 2 energy on Ogerpon-ex to use Myriad Leaf Shower!");
            return;
        }

        int totalEnergy = this.getEnergyAttached() + target.getEnergyAttached();
        int baseDamage = 30 + (20 * totalEnergy);
        int totalDamage = baseDamage + getDamageBoost();

        System.out.println("Ogerpon-ex uses Myriad Leaf Shower!");
        System.out.println("Dealing " + totalDamage + " damage!");
        target.takeDamage(totalDamage);
        resetDamageBoost();

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}
