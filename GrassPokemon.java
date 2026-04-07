public class GrassPokemon extends Pokemon {
    public GrassPokemon(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Type.GRASS, 50, maxHp, attack, defense, speed);
    }

    @Override
    public String getTypeEmoji() {
        return "🌿";
    }
}
