/*public class PokemonBattle {
    public static void main(String[] args) {
        // Create Pokémon
        Pokemon pikachu = new Pokemon("Pikachu", 50, 10, 15);
        Pokemon charmander = new Pokemon("Charmander", 50, 12, 10);

        System.out.println("A wild battle begins! Pikachu vs. Charmander!");

        // Determine attack order
        Pokemon first, second;
        if (pikachu.speed >= charmander.speed) {
            first = pikachu;
            second = charmander;
        } else {
            first = charmander;
            second = pikachu;
        }

        // Battle loop
        while (!pikachu.isFainted() && !charmander.isFainted()) {
            first.attack(second);
            if (second.isFainted()) break;
            second.attack(first);

            System.out.println("Pikachu's health: " + pikachu.health);
            System.out.println("Charmander's health: " + charmander.health);
        }

        System.out.println("Battle over!");
    }
}
*/