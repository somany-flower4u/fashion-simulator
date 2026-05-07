public abstract class ClothingItem {
    private String name;
    private Category category;
    private int warmth;
    private int waterResistance;
    private int formality;
    private int comfort;
    private ClothingProperty[] properties;

    public ClothingItem(String name, Category category, int warmth, int waterResistance, int formality, int comfort) {
        this.name = name;
        this.category = category;
        this.warmth = warmth;
        this.waterResistance = waterResistance;
        this.formality = formality;
        this.comfort = comfort;
        this.properties = new ClothingProperty[4];
    }

    public void setProperties(ClothingProperty p1, ClothingProperty p2, ClothingProperty p3, ClothingProperty p4) {
        properties[0] = p1;
        properties[1] = p2;
        properties[2] = p3;
        properties[3] = p4;
    }

    public abstract String getCategoryEmoji();

    public int calculateScore(Situation situation) {
        double score = 0;
        score += warmth * situation.getWarmthWeight();
        score += waterResistance * situation.getWaterResistanceWeight();
        score += formality * situation.getFormalityWeight();
        score += comfort * situation.getComfortWeight();

        for (ClothingProperty p : properties) {
            if (p != null) {
                score += p.calculateBonus(this, situation);
            }
        }
        return (int) score;
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getWarmth() { return warmth; }
    public int getWaterResistance() { return waterResistance; }
    public int getFormality() { return formality; }
    public int getComfort() { return comfort; }
    public ClothingProperty[] getProperties() { return properties; }
    public ClothingProperty getProperty(int index) { return properties[index]; }

    public String getDisplayName() {
        return name + " " + getCategoryEmoji();
    }
}
