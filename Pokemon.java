public abstract class Pokemon {
    private String name;
    private Type type;
    private int level;
    private int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private int speed;
    private Skill[] skills;
    private int killCount; // 처치 수 (MVP 계산용)

    public Pokemon(String name, Type type, int level, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.skills = new Skill[4];
        this.killCount = 0;
    }

    // 추상 메서드
    public abstract String getTypeEmoji();

    // 스킬 설정
    public void setSkills(Skill s1, Skill s2, Skill s3, Skill s4) {
        skills[0] = s1;
        skills[1] = s2;
        skills[2] = s3;
        skills[3] = s4;
    }

    // 데미지 받기
    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    // 생존 여부
    public boolean isAlive() {
        return currentHp > 0;
    }

    // 사용 가능한 스킬 있는지 확인
    public boolean hasUsableSkill() {
        for (Skill s : skills) {
            if (s != null && s.canUse()) return true;
        }
        return false;
    }

    // HP 바 시각화
    public String getHpBar() {
        int barLength = 20;
        int filled = (int) ((double) currentHp / maxHp * barLength);
        if (currentHp > 0 && filled == 0) filled = 1; // 살아있으면 최소 1칸
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        return bar.toString() + " (" + currentHp + "/" + maxHp + ")";
    }

    // HP 비율에 따른 색상 표시
    public String getHpColor() {
        double ratio = (double) currentHp / maxHp;
        if (ratio > 0.5) return "🟢";
        if (ratio > 0.2) return "🟡";
        return "🔴";
    }

    public void addKill() { killCount++; }

    // 표시용 이름
    public String getDisplayName() {
        return name + " " + getTypeEmoji();
    }

    // Getter
    public String getName() { return name; }
    public Type getType() { return type; }
    public int getLevel() { return level; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public Skill[] getSkills() { return skills; }
    public Skill getSkill(int index) { return skills[index]; }
    public int getKillCount() { return killCount; }
}
