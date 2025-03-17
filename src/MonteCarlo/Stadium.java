package MonteCarlo;

public class Stadium {


    public void battle(Pokemon p1, Pokemon p2) {

        while (p1.getHp() > 0 && p2.getHp() > 0) {

            if (p1.getSpeed() > p2.getSpeed()) {
                p1.attacks(p2);
                p2.attacks(p1);

            } else {
                p2.attacks(p1);
                p1.attacks(p2);

            }

        }
        if (p1.getHp() > p2.getHp()) {
            System.out.println(" Player 1 wins the battle ");
        } else {
            System.out.println(" Player 2 wins the battle ");
        }
    }
}

