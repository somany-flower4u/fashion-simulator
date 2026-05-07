public class ClothingFactory {

    public static ClothingItem[] createAllTops() {
        return new ClothingItem[] {
            createTShirt(),
            createShirt()
        };
    }

    public static ClothingItem[] createAllBottoms() {
        return new ClothingItem[] {
            createJeans(),
            createSlacks()
        };
    }

    public static ClothingItem[] createAllOuters() {
        return new ClothingItem[] {
            createHoodie(),
            createTrenchCoat()
        };
    }

    public static ClothingItem createTShirt() {
        ClothingItem c = new Top("반팔티", 20, 10, 10, 80);
        c.setProperties(
            new ClothingProperty("통기성 소재", 0, 0, 0, 10),
            new ClothingProperty("캐주얼핏", 0, 0, -5, 10),
            null, null
        );
        return c;
    }

    public static ClothingItem createShirt() {
        ClothingItem c = new Top("셔츠", 30, 20, 70, 50);
        c.setProperties(
            new ClothingProperty("깔끔한 칼라", 0, 0, 10, -5),
            new ClothingProperty("면 소재", 5, 5, 0, 5),
            null, null
        );
        return c;
    }

    public static ClothingItem createJeans() {
        ClothingItem c = new Bottom("청바지", 40, 30, 40, 70);
        c.setProperties(
            new ClothingProperty("두꺼운 데님", 10, 5, 0, -5),
            new ClothingProperty("스판", 0, 0, 0, 10),
            null, null
        );
        return c;
    }

    public static ClothingItem createSlacks() {
        ClothingItem c = new Bottom("슬랙스", 35, 25, 80, 50);
        c.setProperties(
            new ClothingProperty("정장 소재", 0, 0, 15, -10),
            new ClothingProperty("깔끔한 핏", 0, 0, 10, -5),
            null, null
        );
        return c;
    }

    public static ClothingItem createHoodie() {
        ClothingItem c = new Outer("후드집업", 60, 30, 20, 80);
        c.setProperties(
            new ClothingProperty("기모 안감", 15, 0, -5, 5),
            new ClothingProperty("캐주얼 스타일", 0, 0, -10, 10),
            null, null
        );
        return c;
    }

    public static ClothingItem createTrenchCoat() {
        ClothingItem c = new Outer("트렌치코트", 50, 60, 80, 40);
        c.setProperties(
            new ClothingProperty("방수 코팅", 0, 20, 0, -5),
            new ClothingProperty("클래식 핏", 0, 0, 15, -5),
            null, null
        );
        return c;
    }

    public static ClothingItem cloneClothing(ClothingItem original) {
        ClothingItem clone;
        switch (original.getCategory()) {
            case TOP:
                clone = new Top(original.getName(), original.getWarmth(),
                    original.getWaterResistance(), original.getFormality(), original.getComfort());
                break;
            case BOTTOM:
                clone = new Bottom(original.getName(), original.getWarmth(),
                    original.getWaterResistance(), original.getFormality(), original.getComfort());
                break;
            case OUTER:
                clone = new Outer(original.getName(), original.getWarmth(),
                    original.getWaterResistance(), original.getFormality(), original.getComfort());
                break;
            default:
                return null;
        }

        ClothingProperty[] origProps = original.getProperties();
        clone.setProperties(
            copyProperty(origProps[0]),
            copyProperty(origProps[1]),
            copyProperty(origProps[2]),
            copyProperty(origProps[3])
        );
        return clone;
    }

    private static ClothingProperty copyProperty(ClothingProperty p) {
        if (p == null) return null;
        return new ClothingProperty(p.getName(), p.getWarmthBonus(),
            p.getWaterBonus(), p.getFormalityBonus(), p.getComfortBonus());
    }
}
