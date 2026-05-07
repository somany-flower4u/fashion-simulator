public class Outfit {
    private ClothingItem top;
    private ClothingItem bottom;
    private ClothingItem outer;

    public Outfit(ClothingItem top, ClothingItem bottom, ClothingItem outer) {
        this.top = top;
        this.bottom = bottom;
        this.outer = outer;
    }

    public int calculateTotalScore(Situation situation) {
        return top.calculateScore(situation)
             + bottom.calculateScore(situation)
             + outer.calculateScore(situation);
    }

    public ClothingItem getTop() { return top; }
    public ClothingItem getBottom() { return bottom; }
    public ClothingItem getOuter() { return outer; }

    public void printOutfit() {
        System.out.println("  👕 상의: " + top.getDisplayName());
        System.out.println("  👖 하의: " + bottom.getDisplayName());
        System.out.println("  🧥 아우터: " + outer.getDisplayName());
    }
}
