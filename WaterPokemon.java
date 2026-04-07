public class WaterPokemon extends Pokemon {
    public WaterPokemon(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Type.WATER, 50, maxHp, attack, defense, speed);
    }

    @Override
    public String getTypeEmoji() {
        return "💧";
    }
}
