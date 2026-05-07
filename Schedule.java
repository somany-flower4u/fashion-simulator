public enum Schedule {
    BUSINESS("정장/회의", "💼"),
    CASUAL("캐주얼 외출", "☕"),
    SPORTS("운동", "🏃"),
    DATE("데이트", "💕");

    private final String korName;
    private final String emoji;

    Schedule(String korName, String emoji) {
        this.korName = korName;
        this.emoji = emoji;
    }

    public String getKorName() { return korName; }
    public String getEmoji() { return emoji; }
}
