import java.util.Scanner;

public class Trainer {
    private String name;
    private Pokemon[] party;
    private int activePokemonIndex;
    private boolean isPlayer;
    private AIStrategy aiStrategy;

    public Trainer(String name, Pokemon[] party, boolean isPlayer) {
        this.name = name;
        this.party = party;
        this.activePokemonIndex = 0;
        this.isPlayer = isPlayer;
        this.aiStrategy = isPlayer ? null : new BasicAIStrategy();
    }

    // 현재 활성 포켓몬
    public Pokemon getActivePokemon() {
        return party[activePokemonIndex];
    }

    // 살아있는 포켓몬 존재 여부
    public boolean hasAlivePokemon() {
        for (Pokemon p : party) {
            if (p.isAlive()) return true;
        }
        return false;
    }

    // 살아있는 포켓몬 수
    public int getAliveCount() {
        int count = 0;
        for (Pokemon p : party) {
            if (p.isAlive()) count++;
        }
        return count;
    }

    // 포켓몬 교체 (플레이어)
    public boolean switchPokemon(int index) {
        if (index < 0 || index >= party.length) return false;
        if (index == activePokemonIndex) return false;
        if (!party[index].isAlive()) return false;
        activePokemonIndex = index;
        return true;
    }

    // 포켓몬 교체 (AI, 상성 고려)
    public int chooseBestSwitch(Pokemon opponentPokemon) {
        int bestIndex = -1;
        double bestScore = -1;

        for (int i = 0; i < party.length; i++) {
            if (i == activePokemonIndex) continue;
            if (!party[i].isAlive()) continue;

            // 상성 유리 = 2.0, 보통 = 1.0, 불리 = 0.5
            double typeScore = party[i].getType().getEffectiveness(opponentPokemon.getType());
            double hpRatio = (double) party[i].getCurrentHp() / party[i].getMaxHp();
            double score = typeScore * 10 + hpRatio * 5;

            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    // AI 스킬 선택 (가장 효과적인 스킬)
    public int chooseBestSkill(Pokemon opponent) {
        Pokemon active = getActivePokemon();
        int bestIndex = 0;
        int bestDamage = 0;

        for (int i = 0; i < active.getSkills().length; i++) {
            Skill skill = active.getSkill(i);
            if (skill == null || !skill.canUse()) continue;

            int estimatedDamage = skill.calculateDamage(active, opponent);
            if (estimatedDamage > bestDamage) {
                bestDamage = estimatedDamage;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    // 강제 교체 (현재 포켓몬 쓰러졌을 때)
    public void forceSwitch(Scanner scanner, Pokemon opponentPokemon) {
        if (!hasAlivePokemon()) return;

        if (isPlayer) {
            System.out.println("\n📢 다음 포켓몬을 선택하세요!");
            printPartyStatus();
            while (true) {
                System.out.print("▶ 번호 입력: ");
                int choice = GameUtils.getValidInput(1, party.length);
                if (party[choice - 1].isAlive() && (choice - 1) != activePokemonIndex) {
                    activePokemonIndex = choice - 1;
                    System.out.println("   ▸ " + name + "은(는) " + getActivePokemon().getDisplayName() + "을(를) 보냈다!");
                    break;
                } else if (!party[choice - 1].isAlive()) {
                    System.out.println("   ❌ 이미 쓰러진 포켓몬입니다.");
                } else {
                    System.out.println("   ❌ 이미 나가있는 포켓몬입니다.");
                }
            }
        } else {
            int bestIdx = chooseBestSwitch(opponentPokemon);
            if (bestIdx >= 0) {
                activePokemonIndex = bestIdx;
                System.out.println("   ▸ " + name + "은(는) " + getActivePokemon().getDisplayName() + "을(를) 보냈다!");
            }
        }
    }

    // 파티 상태 출력
    public void printPartyStatus() {
        for (int i = 0; i < party.length; i++) {
            String marker = (i == activePokemonIndex) ? "▶" : " ";
            String alive = party[i].isAlive() ? party[i].getHpColor() : "❌";
            System.out.printf("  %s %d. %-6s %s HP: %s\n",
                    marker, (i + 1), party[i].getDisplayName(), alive,
                    party[i].isAlive() ? party[i].getHpBar() : "쓰러짐");
        }
    }

    // MVP 찾기
    public Pokemon getMVP() {
        Pokemon mvp = party[0];
        for (Pokemon p : party) {
            if (p.getKillCount() > mvp.getKillCount()) {
                mvp = p;
            }
        }
        return mvp;
    }

    // 유효 입력 헬퍼
    private int getValidInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
                System.out.printf("   ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("   ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            }
        }
    }

    // Getter
    public String getName() { return name; }
    public Pokemon[] getParty() { return party; }
    public boolean isPlayer() { return isPlayer; }
    public AIStrategy getAIStrategy() { return aiStrategy; }
    public void setAIStrategy(AIStrategy strategy) { this.aiStrategy = strategy; }

    /**
     * 지정된 인덱스의 포켓몬 조회 (Encapsulation)
     */
    public Pokemon getPokemon(int index) {
        if (index < 0 || index >= party.length) return null;
        return party[index];
    }
}
