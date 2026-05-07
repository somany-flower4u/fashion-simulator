public class Wardrobe {
    private String ownerName;
    private ClothingItem[] tops;
    private ClothingItem[] bottoms;
    private ClothingItem[] outers;

    public Wardrobe(String ownerName, ClothingItem[] tops, ClothingItem[] bottoms, ClothingItem[] outers) {
        this.ownerName = ownerName;
        this.tops = tops;
        this.bottoms = bottoms;
        this.outers = outers;
    }

    public ClothingItem[] getTops() { return tops; }
    public ClothingItem[] getBottoms() { return bottoms; }
    public ClothingItem[] getOuters() { return outers; }
    public String getOwnerName() { return ownerName; }

    public void printWardrobe() {
        System.out.println("\n📋 " + ownerName + "의 옷장");
        System.out.println("┌─────────┬──────────┬──────┬──────┬──────┬──────┐");
        System.out.println("│ 카테고리 │ 이름      │ 보온  │ 방수  │ 격식  │ 편안  │");
        System.out.println("├─────────┼──────────┼──────┼──────┼──────┼──────┤");
        for (ClothingItem c : tops) printItem(c);
        for (ClothingItem c : bottoms) printItem(c);
        for (ClothingItem c : outers) printItem(c);
        System.out.println("└─────────┴──────────┴──────┴──────┴──────┴──────┘");
    }

    private void printItem(ClothingItem c) {
        System.out.printf("│   %s   │ %-6s  │ %-3d │ %-3d │ %-3d │ %-3d │\n",
            c.getCategory().getEmoji(), c.getName(),
            c.getWarmth(), c.getWaterResistance(), c.getFormality(), c.getComfort());
    }
}
