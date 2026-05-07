public class Outer extends ClothingItem {
    public Outer(String name, int warmth, int waterResistance, int formality, int comfort) {
        super(name, Category.OUTER, warmth, waterResistance, formality, comfort);
    }

    @Override
    public String getCategoryEmoji() {
        return "🧥";
    }
}
