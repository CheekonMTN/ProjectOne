package MonteCarlo;

public class Pokemon {
    private int hp;
    private int attack;
    private int defense;
    private int speed;

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHp(int userHP) {
        hp = userHP;
    }

    public void setAttack(int userAttack) {
        attack = userAttack;

    }

    public void setDefense(int userDefense) {
        defense = userDefense;
    }

    public void setSpeed(int userSpeed) {
        speed = userSpeed;
    }

    public void attacks( Pokemon pk2) {
        pk2.setHp(pk2.getHp()-(getAttack()-pk2.getDefense()));

    }
}