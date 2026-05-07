public enum Category {
    TOP("상의", "👕"),
    BOTTOM("하의", "👖"),
    OUTER("아우터", "🧥");

    private final String korName;
    private final String emoji;

    Category(String korName, String emoji) {
        this.korName = korName;
        this.emoji = emoji;
    }

    public String getKorName() { return korName; }
    public String getEmoji() { return emoji; }
}
