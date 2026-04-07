import java.util.Scanner;

public class BattleManager {
    private Trainer player;
    private Trainer rival;
    private int turnCount;
    private Scanner scanner;

    public BattleManager(Trainer player, Trainer rival, Scanner scanner) {
        this.player = player;
        this.rival = rival;
        this.turnCount = 0;
        this.scanner = scanner;
    }

    // 배틀 시작
    public void startBattle() {
        printBattleIntro();
        battleLoop();
        announceResult();
    }

    // 배틀 인트로
    private void printBattleIntro() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              ⚔ 배틀 시작! ⚔                ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("  🔵 " + player.getName() + "의 " + player.getActivePokemon().getDisplayName() + " VS "
                + rival.getActivePokemon().getDisplayName() + " " + rival.getName() + "의 🔴");
        pause(1000);
    }

    // 메인 배틀 루프
    private void battleLoop() {
        while (player.hasAlivePokemon() && rival.hasAlivePokemon()) {
            turnCount++;
            printBattleStatus();

            // 플레이어 행동 선택
            int playerAction = getPlayerAction();
            int playerSkillIndex = -1;
            int playerSwitchIndex = -1;

            if (playerAction == 1) {
                // 공격
                playerSkillIndex = getPlayerSkillChoice();
            } else {
                // 교체
                playerSwitchIndex = getPlayerSwitchChoice();
            }

            // AI 행동 결정
            int rivalAction = getRivalAction();
            int rivalSkillIndex = -1;
            int rivalSwitchIndex = -1;

            if (rivalAction == 1) {
                rivalSkillIndex = rival.chooseBestSkill(player.getActivePokemon());
            } else {
                rivalSwitchIndex = rival.chooseBestSwitch(player.getActivePokemon());
            }

            System.out.println("\n─────────────────────────────────────");

            // 턴 실행
            executeTurn(playerAction, playerSkillIndex, playerSwitchIndex,
                    rivalAction, rivalSkillIndex, rivalSwitchIndex);

            System.out.println("─────────────────────────────────────");

            // 쓰러진 포켓몬 처리
            handleFainted();

            pause(500);
        }
    }

    // 배틀 상태 출력
    private void printBattleStatus() {
        System.out.println("\n═══════════ ⚔ TURN " + turnCount + " ═══════════");
        System.out.println();

        // 라이벌 포켓몬
        Pokemon rp = rival.getActivePokemon();
        System.out.println("  🔴 " + rival.getName() + ": " + rp.getDisplayName() + " Lv." + rp.getLevel());
        System.out.println("     HP: " + rp.getHpColor() + " " + rp.getHpBar());
        System.out.println("     남은 포켓몬: " + rival.getAliveCount() + "마리");
        System.out.println();

        // 플레이어 포켓몬
        Pokemon pp = player.getActivePokemon();
        System.out.println("  🔵 " + player.getName() + ": " + pp.getDisplayName() + " Lv." + pp.getLevel());
        System.out.println("     HP: " + pp.getHpColor() + " " + pp.getHpBar());
        System.out.println("     남은 포켓몬: " + player.getAliveCount() + "마리");
        System.out.println();
    }

    // 플레이어 행동 선택
    private int getPlayerAction() {
        System.out.println("  ▶ 행동을 선택하세요");
        System.out.println("    1. ⚔️  공격");
        System.out.println("    2. 🔄 포켓몬 교체");
        System.out.print("  ▶ 선택: ");
        return getValidInput(1, 2);
    }

    // 플레이어 스킬 선택
    private int getPlayerSkillChoice() {
        Pokemon active = player.getActivePokemon();
        System.out.println("\n  ▶ 스킬을 선택하세요");
        Skill[] skills = active.getSkills();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null) {
                String usable = skills[i].canUse() ? "  " : "❌";
                System.out.printf("    %d. %s %s\n", (i + 1), usable, skills[i].getInfo());
            }
        }

        while (true) {
            System.out.print("  ▶ 선택: ");
            int choice = getValidInput(1, 4);
            if (skills[choice - 1] != null && skills[choice - 1].canUse()) {
                return choice - 1;
            }
            System.out.println("    ❌ PP가 부족합니다! 다른 스킬을 선택하세요.");
        }
    }

    // 플레이어 교체 선택
    private int getPlayerSwitchChoice() {
        System.out.println("\n  ▶ 교체할 포켓몬을 선택하세요");
        player.printPartyStatus();

        while (true) {
            System.out.print("  ▶ 번호 입력: ");
            int choice = getValidInput(1, 3);
            Pokemon target = player.getParty()[choice - 1];
            if (target == player.getActivePokemon()) {
                System.out.println("    ❌ 이미 나가있는 포켓몬입니다.");
            } else if (!target.isAlive()) {
                System.out.println("    ❌ 이미 쓰러진 포켓몬입니다.");
            } else {
                return choice - 1;
            }
        }
    }

    // AI 행동 결정
    private int getRivalAction() {
        Pokemon rivalActive = rival.getActivePokemon();
        Pokemon playerActive = player.getActivePokemon();

        // HP가 30% 이하이고, 상성 불리하면 교체 고려
        double hpRatio = (double) rivalActive.getCurrentHp() / rivalActive.getMaxHp();
        double typeEff = playerActive.getType().getEffectiveness(rivalActive.getType());

        if (hpRatio < 0.3 && typeEff >= 2.0 && rival.getAliveCount() > 1) {
            int bestSwitch = rival.chooseBestSwitch(playerActive);
            if (bestSwitch >= 0) {
                // 교체 대상이 현재보다 나은지 확인
                Pokemon switchTarget = rival.getParty()[bestSwitch];
                double newTypeEff = playerActive.getType().getEffectiveness(switchTarget.getType());
                if (newTypeEff < typeEff) {
                    return 2; // 교체
                }
            }
        }
        return 1; // 공격
    }

    // 턴 실행
    private void executeTurn(int pAction, int pSkill, int pSwitch,
                             int rAction, int rSkill, int rSwitch) {
        // 1단계: 교체 먼저 처리
        if (pAction == 2) {
            player.switchPokemon(pSwitch);
            System.out.println("  🔵 " + player.getName() + "은(는) " + player.getActivePokemon().getDisplayName() + "(으)로 교체했다!");
            pause(500);
        }
        if (rAction == 2 && rSwitch >= 0) {
            rival.switchPokemon(rSwitch);
            System.out.println("  🔴 " + rival.getName() + "은(는) " + rival.getActivePokemon().getDisplayName() + "(으)로 교체했다!");
            pause(500);
        }

        // 2단계: 공격 처리
        boolean playerAttacks = (pAction == 1);
        boolean rivalAttacks = (rAction == 1);

        if (playerAttacks && rivalAttacks) {
            // 둘 다 공격: 선제공격 > 스피드 순서
            Skill pSkillObj = player.getActivePokemon().getSkill(pSkill);
            Skill rSkillObj = rival.getActivePokemon().getSkill(rSkill);

            boolean playerFirst = determineOrder(
                    player.getActivePokemon(), pSkillObj,
                    rival.getActivePokemon(), rSkillObj);

            if (playerFirst) {
                executeAttack(player, rival, pSkillObj);
                if (rival.getActivePokemon().isAlive()) {
                    executeAttack(rival, player, rSkillObj);
                }
            } else {
                executeAttack(rival, player, rSkillObj);
                if (player.getActivePokemon().isAlive()) {
                    executeAttack(player, rival, pSkillObj);
                }
            }
        } else if (playerAttacks) {
            Skill pSkillObj = player.getActivePokemon().getSkill(pSkill);
            executeAttack(player, rival, pSkillObj);
        } else if (rivalAttacks) {
            Skill rSkillObj = rival.getActivePokemon().getSkill(rSkill);
            executeAttack(rival, player, rSkillObj);
        }
    }

    // 공격 순서 결정
    private boolean determineOrder(Pokemon p1, Skill s1, Pokemon p2, Skill s2) {
        // 선제공격 스킬 우선
        if (s1.isPriority() && !s2.isPriority()) {
            System.out.println("  ⚡ " + p1.getName() + "의 " + s1.getName() + "은(는) 선제공격!");
            return true;
        }
        if (s2.isPriority() && !s1.isPriority()) {
            System.out.println("  ⚡ " + p2.getName() + "의 " + s2.getName() + "은(는) 선제공격!");
            return false;
        }

        // 스피드 비교
        if (p1.getSpeed() != p2.getSpeed()) {
            boolean p1Faster = p1.getSpeed() > p2.getSpeed();
            System.out.printf("  ⚡ %s(속도:%d) %s %s(속도:%d)\n",
                    p1.getName(), p1.getSpeed(),
                    p1Faster ? ">" : "<",
                    p2.getName(), p2.getSpeed());
            System.out.println("     " + (p1Faster ? p1.getName() : p2.getName()) + "이(가) 먼저 공격!");
            return p1Faster;
        }

        // 스피드 동일: 랜덤
        boolean coinFlip = Math.random() < 0.5;
        System.out.println("  ⚡ 속도가 같다! " + (coinFlip ? p1.getName() : p2.getName()) + "이(가) 먼저!");
        return coinFlip;
    }

    // 공격 실행
    private void executeAttack(Trainer attacker, Trainer defender, Skill skill) {
        Pokemon atkPokemon = attacker.getActivePokemon();
        Pokemon defPokemon = defender.getActivePokemon();

        if (!atkPokemon.isAlive()) return;

        String tag = attacker.isPlayer() ? "🔵" : "🔴";

        // PP 소모
        skill.usePP();

        System.out.println("\n  " + tag + " " + atkPokemon.getName() + "의 " + skill.getName() + "!");
        pause(400);

        // 명중 판정
        if (!skill.tryHit()) {
            System.out.println("     ▸ 하지만 빗나갔다!");
            return;
        }

        // 데미지 계산
        int damage = skill.calculateDamage(atkPokemon, defPokemon);
        String effMsg = skill.getType().getEffectivenessMessage(defPokemon.getType());

        defPokemon.takeDamage(damage);

        System.out.println("     ▸ " + effMsg + " (x" + skill.getType().getEffectiveness(defPokemon.getType()) + ")");
        System.out.println("     ▸ " + defPokemon.getName() + "에게 " + damage + " 데미지!");
        System.out.println("     ▸ " + defPokemon.getName() + " HP: " + defPokemon.getHpBar());

        // 쓰러짐 체크
        if (!defPokemon.isAlive()) {
            System.out.println("\n     ❌ " + defPokemon.getName() + "은(는) 쓰러졌다!");
            atkPokemon.addKill();
        }

        pause(300);
    }

    // 쓰러진 포켓몬 처리
    private void handleFainted() {
        // 라이벌 포켓몬 쓰러짐
        if (!rival.getActivePokemon().isAlive() && rival.hasAlivePokemon()) {
            System.out.println("\n  🔴 " + rival.getName() + "의 남은 포켓몬: " + rival.getAliveCount() + "마리");
            rival.forceSwitch(scanner, player.getActivePokemon());
        }

        // 플레이어 포켓몬 쓰러짐
        if (!player.getActivePokemon().isAlive() && player.hasAlivePokemon()) {
            System.out.println("\n  🔵 " + player.getName() + "의 남은 포켓몬: " + player.getAliveCount() + "마리");
            player.forceSwitch(scanner, rival.getActivePokemon());
        }
    }

    // 결과 발표
    private void announceResult() {
        System.out.println("\n═══════════════════════════════════════");

        boolean playerWins = player.hasAlivePokemon();

        if (playerWins) {
            System.out.println("  🏆 축하합니다! 배틀에서 승리했습니다! 🏆");
        } else {
            System.out.println("  💀 아쉽게도 패배했습니다... 💀");
        }

        System.out.println("\n  📊 배틀 결과");
        System.out.println("  ─────────────────────────────");
        System.out.println("  총 턴 수  : " + turnCount + "턴");
        System.out.println("  남은 포켓몬: " + player.getAliveCount() + "마리");

        Pokemon mvp = player.getMVP();
        System.out.println("  MVP       : " + mvp.getDisplayName() + " (처치: " + mvp.getKillCount() + "마리)");
        System.out.println("\n  📋 파티 최종 상태:");
        player.printPartyStatus();

        System.out.println("═══════════════════════════════════════");
    }

    // 입력 유효성 검사
    private int getValidInput(int min, int max) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
                System.out.printf("    ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("    ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            }
        }
    }

    // 딜레이
    private void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // 무시
        }
    }
}
