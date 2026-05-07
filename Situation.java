public class Situation {
    private Weather weather;
    private Schedule schedule;
    private int temperature;

    public Situation(Weather weather, Schedule schedule, int temperature) {
        this.weather = weather;
        this.schedule = schedule;
        this.temperature = temperature;
    }

    public double getWarmthWeight() {
        double weight = 1.0;
        if (temperature < 5) weight += 1.5;
        else if (temperature < 15) weight += 0.5;
        else if (temperature > 30) weight -= 0.5;

        if (weather == Weather.SNOWY || weather == Weather.COLD) weight += 0.5;
        if (weather == Weather.HOT) weight -= 0.5;
        return Math.max(0.1, weight);
    }

    public double getWaterResistanceWeight() {
        double weight = 1.0;
        if (weather == Weather.RAINY) weight += 2.0;
        if (weather == Weather.SNOWY) weight += 1.0;
        return Math.max(0.1, weight);
    }

    public double getFormalityWeight() {
        double weight = 1.0;
        if (schedule == Schedule.BUSINESS) weight += 1.5;
        if (schedule == Schedule.CASUAL) weight -= 0.3;
        if (schedule == Schedule.DATE) weight += 0.5;
        return Math.max(0.1, weight);
    }

    public double getComfortWeight() {
        double weight = 1.0;
        if (schedule == Schedule.SPORTS) weight += 2.0;
        if (schedule == Schedule.CASUAL) weight += 0.5;
        if (schedule == Schedule.BUSINESS) weight -= 0.3;
        return Math.max(0.1, weight);
    }

    public Weather getWeather() { return weather; }
    public Schedule getSchedule() { return schedule; }
    public int getTemperature() { return temperature; }

    public String getDisplay() {
        return String.format("%s %s / %s / 기온 %d℃",
            weather.getEmoji(), weather.getKorName(), schedule.getKorName(), temperature);
    }
}
