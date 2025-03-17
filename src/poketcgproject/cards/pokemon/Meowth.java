package poketcgproject.cards.pokemon;
import poketcgproject.PTCG;

public class Meowth extends Pokemon {
    private PTCG game;

    public Meowth(PTCG game) {
        super("Meowth", 120, "Fury Swipes", 40, 1, 1, game);
        this.game = game;
    }

    @Override
    public void attack(Pokemon target) {
        if (this.getEnergyAttached() >= this.getEnergyCost()) {
            System.out.println(this.getName() + " uses " + this.getAttackName() + "!");
            System.out.println("Flipping 3 coins...");
            int hits = 0;
            for (int i = 1; i <= 3; i++) {
                boolean isHeads = Math.random() < 0.5;
                System.out.println("Coin " + i + ": " + (isHeads ? "Heads" : "Tails"));
                if (isHeads) hits++;
            }

            int baseDamage = this.getAttackDamage() * hits;
            int totalDamage = baseDamage + getDamageBoost();
            System.out.println("Dealing " + totalDamage + " damage!");
            target.takeDamage(totalDamage);
            System.out.println(target.getName() + "'s remaining HP: " + target.getHp());
            resetDamageBoost();
        } else {
            System.out.println("Not enough energy to attack!");
        }
    }
}