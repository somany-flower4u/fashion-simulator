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
}
