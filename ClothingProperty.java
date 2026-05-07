public class ClothingProperty {
    private String name;
    private int warmthBonus;
    private int waterBonus;
    private int formalityBonus;
    private int comfortBonus;

    public ClothingProperty(String name, int warmthBonus, int waterBonus, int formalityBonus, int comfortBonus) {
        this.name = name;
        this.warmthBonus = warmthBonus;
        this.waterBonus = waterBonus;
        this.formalityBonus = formalityBonus;
        this.comfortBonus = comfortBonus;
    }

    public int calculateBonus(ClothingItem item, Situation situation) {
        double bonus = 0;
        bonus += warmthBonus * situation.getWarmthWeight();
        bonus += waterBonus * situation.getWaterResistanceWeight();
        bonus += formalityBonus * situation.getFormalityWeight();
        bonus += comfortBonus * situation.getComfortWeight();
        return (int) bonus;
    }

    public String getName() { return name; }
    public int getWarmthBonus() { return warmthBonus; }
    public int getWaterBonus() { return waterBonus; }
    public int getFormalityBonus() { return formalityBonus; }
    public int getComfortBonus() { return comfortBonus; }

    public String getInfo() {
        return String.format("%s (보온%+d 방수%+d 격식%+d 편안%+d)",
            name, warmthBonus, waterBonus, formalityBonus, comfortBonus);
    }
}
