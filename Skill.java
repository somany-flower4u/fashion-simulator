public class Skill {
    private String name;
    private Type type;
    private int power;
    private int accuracy;
    private int maxPP;
    private int currentPP;
    private boolean isPriority; // 선제공격 여부

    public Skill(String name, Type type, int power, int accuracy, int maxPP, boolean isPriority) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPP = maxPP;
        this.currentPP = maxPP;
        this.isPriority = isPriority;
    }

    // 데미지 계산
    public int calculateDamage(Pokemon attacker, Pokemon defender) {
        double baseDamage = ((double) attacker.getAttack() / defender.getDefense() * power * 0.5);
        double typeMultiplier = type.getEffectiveness(defender.getType());
        double randomFactor = 0.85 + Math.random() * 0.15;
        int damage = (int) (baseDamage * typeMultiplier * randomFactor);
        return Math.max(1, damage); // 최소 1 데미지
    }

    // 명중 판정
    public boolean tryHit() {
        return (int) (Math.random()* 100) < accuracy;
    }

    // PP 사용
    public boolean usePP() {
        if (currentPP <= 0) return false;
        currentPP--;
        return true;
    }

    public boolean canUse() {
        return currentPP > 0;
    }

    // Getter
    public String getName() { return name; }
    public Type getType() { return type; }
    public int getPower() { return power; }
    public int getAccuracy() { return accuracy; }
    public int getMaxPP() { return maxPP; }
    public int getCurrentPP() { return currentPP; }
    public boolean isPriority() { return isPriority; }

    // 스킬 정보 문자열
    public String getInfo() {
        return String.format("%-8s %s 위력:%-3d 명중:%-3d PP(%d/%d)%s",
                name, type.getEmoji(), power, accuracy, currentPP, maxPP,
                isPriority ? " ⚡선제" : "");
    }
}
