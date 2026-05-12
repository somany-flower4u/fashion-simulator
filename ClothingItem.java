public class ClothingItem {
    private String name;
    private Category category;
    private int warmth;
    private int waterResistance;
    private int formality;
    private int comfort;
    private Property[] properties;

    public ClothingItem(String name, Category category, int warmth, int waterResistance, int formality, int comfort) {
        this.name = name;
        this.category = category;
        this.warmth = warmth;
        this.waterResistance = waterResistance;
        this.formality = formality;
        this.comfort = comfort;
        this.properties = new Property[4];
    }

    public void setProperties(Property p1, Property p2, Property p3, Property p4) {
        properties[0] = p1;
        properties[1] = p2;
        properties[2] = p3;
        properties[3] = p4;
    }

    public String getCategoryEmoji() {
        return category.getEmoji();
    }

    public int calculateScore(Situation situation) {
        double score = 0;
        score += warmth * situation.getWarmthWeight();
        score += waterResistance * situation.getWaterResistanceWeight();
        score += formality * situation.getFormalityWeight();
        score += comfort * situation.getComfortWeight();

        for (Property p : properties) {
            if (p != null) {
                score += p.calculateBonus(this, situation);
            }
        }

        if (situation.getTemperature() > 30 && category == Category.OUTER && warmth > 0) {
            score -= 500;
        }

        return (int) score;
    }

    public ClothingItem cloneItem() {
        ClothingItem clone = new ClothingItem(name, category, warmth, waterResistance, formality, comfort);
        clone.setProperties(
            copyProperty(properties[0]),
            copyProperty(properties[1]),
            copyProperty(properties[2]),
            copyProperty(properties[3])
        );
        return clone;
    }

    private Property copyProperty(Property p) {
        if (p == null) return null;
        return new Property(p.getName(), p.getWarmthBonus(), p.getWaterBonus(), p.getFormalityBonus(), p.getComfortBonus());
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getWarmth() { return warmth; }
    public int getWaterResistance() { return waterResistance; }
    public int getFormality() { return formality; }
    public int getComfort() { return comfort; }
    public Property[] getProperties() { return properties; }
    public Property getProperty(int index) { return properties[index]; }

    public String getDisplayName() {
        return name + " " + getCategoryEmoji();
    }

    public static class Property {
        private String name;
        private int warmthBonus;
        private int waterBonus;
        private int formalityBonus;
        private int comfortBonus;

        public Property(String name, int warmthBonus, int waterBonus, int formalityBonus, int comfortBonus) {
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
}
