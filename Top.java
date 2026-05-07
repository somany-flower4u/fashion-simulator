public class Top extends ClothingItem {
    public Top(String name, int warmth, int waterResistance, int formality, int comfort) {
        super(name, Category.TOP, warmth, waterResistance, formality, comfort);
    }

    @Override
    public String getCategoryEmoji() {
        return "👕";
    }
}
