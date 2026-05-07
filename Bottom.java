public class Bottom extends ClothingItem {
    public Bottom(String name, int warmth, int waterResistance, int formality, int comfort) {
        super(name, Category.BOTTOM, warmth, waterResistance, formality, comfort);
    }

    @Override
    public String getCategoryEmoji() {
        return "👖";
    }
}
