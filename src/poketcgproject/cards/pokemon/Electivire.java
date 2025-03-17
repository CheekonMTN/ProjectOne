package poketcgproject.cards.pokemon;

import poketcgproject.PTCG;
public class Electivire extends Pokemon {
    public Electivire(PTCG game) {
        super("Electivire", 120, "Discharge", 80, 3, 3, game);
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        int energyCount = this.getEnergyAttached();

        if (energyCount < getEnergyCost()){
            System.out.println("You need at least 3 energy on Electivire to use Discharge!");
            return;
        }

        int baseDamage = 80;
        System.out.println("Electivire uses Discharge!");
        System.out.println("Flipping " + energyCount + " coins...");

        for (int i = 0; i < energyCount; i++) {
            boolean heads = Math.random() < 0.5;
            System.out.println("Coin " + (i + 1) + ": " + (heads ? "Heads" : "Tails"));
            if (heads) {
                baseDamage += 80;
            }
        }

        int totalDamage = baseDamage + getDamageBoost();
        System.out.println("Dealing " + totalDamage + " damage!");
        target.takeDamage(totalDamage);
        this.removeAllEnergy();
        resetDamageBoost();

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}