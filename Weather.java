public enum Weather {
    SUNNY("맑음", "☀️"),
    RAINY("비", "🌧️"),
    SNOWY("눈", "❄️"),
    COLD("추움", "🥶"),
    HOT("더움", "🥵"),
    WINDY("바람", "💨");

    private final String korName;
    private final String emoji;

    Weather(String korName, String emoji) {
        this.korName = korName;
        this.emoji = emoji;
    }

    public String getKorName() { return korName; }
    public String getEmoji() { return emoji; }
}
