public enum Type {
    FIRE("불", "🔥"),
    WATER("물", "💧"),
    GRASS("풀", "🌿"),
    NORMAL("무", "⬜");

    private final String korName;
    private final String emoji;

    Type(String korName, String emoji) {
        this.korName = korName;
        this.emoji = emoji;
    }

    public String getKorName() {
        return korName;
    }

    public String getEmoji() {
        return emoji;
    }

    // 상성 배율 계산
    public double getEffectiveness(Type defender) {
        if (this == FIRE && defender == GRASS) return 2.0;
        if (this == WATER && defender == FIRE) return 2.0;
        if (this == GRASS && defender == WATER) return 2.0;
        if (this == FIRE && defender == WATER) return 0.5;
        if (this == WATER && defender == GRASS) return 0.5;
        if (this == GRASS && defender == FIRE) return 0.5;
        return 1.0;
    }

    // 상성 메시지
    public String getEffectivenessMessage(Type defender) {
        double mult = getEffectiveness(defender);
        if (mult >= 2.0) return "💥 효과가 굉장했다!";
        if (mult <= 0.5) return "😑 효과가 별로인 듯하다...";
        return "👊 효과가 보통이다.";
    }
}
