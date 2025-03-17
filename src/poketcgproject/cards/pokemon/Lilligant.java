package poketcgproject.cards.pokemon;
/**
 * Hisuian Lilligant VStar card : Chrees' card
 * Base HP: 140
 * Attack: Parallel Spin (100 base damage)
 * Energy Cost: 3
 * Special Effect: Discard 1 energy after attack and
 * add 1 energy card from your deck to your hand
 */
import poketcgproject.PTCG;
import poketcgproject.cards.energy.Energy;
import poketcgproject.Player;

public class Lilligant extends Pokemon {
    private PTCG game;

    public Lilligant(PTCG game) {
        super("Hisuian Lilligant VStar", 140, "Parallel Spin", 100, 3, 2, game);
        this.game = game;
    }

    @Override
    public void attack(Pokemon target) {
        if (target == null) {
            System.out.println("No target to attack!");
            return;
        }

        if (getEnergyAttached() < getEnergyCost()) {
            System.out.println("You need at least 2 energy on Hisuian Lilligant VStar to use Parallel Spin!");
            return;
        }

        System.out.println("Hisuian Lilligant VStar uses Parallel Spin!");
        target.takeDamage(getAttackDamage());

        removeEnergy(1);
        System.out.println("Removed 1 energy from Hisuian Lilligant VStar");

        // Get current player state when needed
        Player currentPlayer = game.getCurrentPlayerState();
        if (currentPlayer != null) {
            currentPlayer.addCardToHand(new Energy());
            System.out.println("Added 1 energy card to hand");
        }

        if (target.knockedOut()) {
            System.out.println(target.getName() + " was knocked out!");
        }
    }
}