public class FirePokemon extends Pokemon {
    public FirePokemon(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Type.FIRE, 50, maxHp, attack, defense, speed);
    }

    @Override
    public String getTypeEmoji() {
        return "🔥";
    }
}
