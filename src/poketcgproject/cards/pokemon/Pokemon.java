package poketcgproject.cards.pokemon;
/**
 * Abstract class used by all pokemon cards
 * Manages all of their stats and attacks
 * Attacks are overridden in each respective pokemon class
 */

import poketcgproject.PTCG;
import poketcgproject.cards.Card;
import poketcgproject.Player;
public abstract class Pokemon extends Card {
    private int hp;
    private int attackDamage;
    private String attackName;
    private int energyCost;
    private int energyAttached;
    private int retreatCost;
    private boolean hasSummoningSickness;
    protected int damageBoost;
    private PTCG controller;
    private Player playerState;

    public Pokemon(String name, int hp, String attackName, int attackDamage, int energyCost, int retreatCost, PTCG controller) {
        super(name); // Call Card constructor with name
        this.hp = hp;
        this.attackName = attackName;
        this.attackDamage = attackDamage;
        this.energyCost = energyCost;
        this.energyAttached = 0;
        this.retreatCost = retreatCost;
        this.hasSummoningSickness = true;
        this.controller = controller;
    }

    public void setPlayerState(Player playerState) {
        this.playerState = playerState;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public String getAttackName() {
        return attackName;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getEnergyAttached() {
        return energyAttached;
    }

    public void addEnergy() {
        this.energyAttached++;
    }

    public boolean hasSummoningSickness() {
        return hasSummoningSickness;
    }

    public void removeSummoningSickness() { //This method called when pokemon in turn for at least one turn
        this.hasSummoningSickness = false;
    }

    public void removeEnergy(int count) {
        this.energyAttached -= count;
        if (this.energyAttached < 0) {
            this.energyAttached = 0;
        }
    }

    public void removeAllEnergy() {
        this.energyAttached = 0;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    public int getRetreatCost() {
        return retreatCost;
    }

    public void heal(int healAmount) {
        this.hp += healAmount;
    }

    public boolean knockedOut() {
        return this.hp <= 0;
    }

    public void attack(Pokemon target) {
        if (this.energyAttached >= energyCost) {
            int totalDamage = this.attackDamage + this.damageBoost;
            System.out.println(this.name + " uses " + this.attackName + "!");
            System.out.println("Dealt " + totalDamage + " damage to " + target.getName() + "!");
            target.takeDamage(totalDamage);
            System.out.println(target.getName() + "'s remaining HP: " + target.getHp());
            this.damageBoost = 0; // Reset after attack
        } else {
            System.out.println("Not enough energy to attack!");
        }
    }

    public void addDamageBoost(int boost){
        this.damageBoost += boost;
    }

    protected int getDamageBoost() {
        return damageBoost;
    }

    protected void resetDamageBoost() {
        this.damageBoost = 0;
    }
}