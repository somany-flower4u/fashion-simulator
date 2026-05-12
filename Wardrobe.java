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

    public static class Factory {
        public static ClothingItem[] createAllTops() {
            return new ClothingItem[] {
                createTShirt(),
                createShirt()
            };
        }

        public static ClothingItem[] createAllBottoms() {
            return new ClothingItem[] {
                createJeans(),
                createSlacks(),
                createTrainingPants()
            };
        }

        public static ClothingItem[] createAllOuters() {
            return new ClothingItem[] {
                createHoodie(),
                createTrenchCoat(),
                createNULLCoat()
            };
        }

        public static ClothingItem createTShirt() {
            ClothingItem c = new ClothingItem("반팔티", Category.TOP, 20, 10, 10, 80);
            c.setProperties(
                new ClothingItem.Property("통기성 소재", 0, 0, 0, 10),
                new ClothingItem.Property("캐주얼핏", 0, 0, -5, 10),
                null, null
            );
            return c;
        }

        public static ClothingItem createShirt() {
            ClothingItem c = new ClothingItem("셔츠", Category.TOP, 30, 20, 70, 50);
            c.setProperties(
                new ClothingItem.Property("깔끔한 칼라", 0, 0, 10, -5),
                new ClothingItem.Property("면 소재", 5, 5, 0, 5),
                null, null
            );
            return c;
        }

        public static ClothingItem createJeans() {
            ClothingItem c = new ClothingItem("청바지", Category.BOTTOM, 40, 30, 40, 70);
            c.setProperties(
                new ClothingItem.Property("두꺼운 데님", 10, 5, 0, -5),
                new ClothingItem.Property("스판", 0, 0, 0, 10),
                null, null
            );
            return c;
        }

        public static ClothingItem createSlacks() {
            ClothingItem c = new ClothingItem("슬랙스", Category.BOTTOM, 35, 25, 80, 50);
            c.setProperties(
                new ClothingItem.Property("정장 소재", 0, 0, 15, -10),
                new ClothingItem.Property("깔끔한 핏", 0, 0, 10, -5),
                null, null
            );
            return c;
        }

        public static ClothingItem createTrainingPants() {
            ClothingItem c = new ClothingItem("트레이닝 팬츠", Category.BOTTOM, 25, 20, 20, 90);
            c.setProperties(
                new ClothingItem.Property("스포티한 스타일", 0, 0, -10, 10),
                new ClothingItem.Property("편안한 착용감", 0, 0, 0, 15),
                null, null
            );
            return c;
        }

        public static ClothingItem createHoodie() {
            ClothingItem c = new ClothingItem("후드집업", Category.OUTER, 60, 30, 20, 80);
            c.setProperties(
                new ClothingItem.Property("기모 안감", 15, 0, -5, 5),
                new ClothingItem.Property("캐주얼 스타일", 0, 0, -10, 10),
                null, null
            );
            return c;
        }

        public static ClothingItem createTrenchCoat() {
            ClothingItem c = new ClothingItem("트렌치코트", Category.OUTER, 50, 60, 80, 40);
            c.setProperties(
                new ClothingItem.Property("방수 코팅", 0, 20, 0, -5),
                new ClothingItem.Property("클리식 핏", 0, 0, 15, -5),
                null, null
            );
            return c;
        }

        public static ClothingItem createNULLCoat() {
            ClothingItem c = new ClothingItem("아우터 없음 !", Category.OUTER, 0, 0, 0, 100);
            c.setProperties(
                new ClothingItem.Property("아우터 없음", 0, 0, 0, 0),
                new ClothingItem.Property("아우터 없음", 0, 0, 0, 0),
                null, null
            );
            return c;
        }
    }
}
