import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 게임 유틸리티 초기화
        new GameUtils(scanner);

        printTitle();

        boolean playAgain = true;
        while (playAgain) {
            // 포켓몬 도감 생성
            Pokemon[] pokedex = PokemonFactory.createAllPokemon();

            // 플레이어 포켓몬 선택
            Pokemon[] playerParty = selectPlayerParty(pokedex);

            // 라이벌 포켓몬 랜덤 배정
            Pokemon[] rivalParty = selectRivalParty(pokedex);

            // 트레이너 생성
            Trainer player = new Trainer("플레이어", playerParty, true);
            Trainer rival = new Trainer("라이벌", rivalParty, false);

            // 라이벌 포켓몬 공개
            System.out.println("\n🆚 라이벌이 포켓몬을 보냈다!");
            System.out.print("   [");
            for (int i = 0; i < rivalParty.length; i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(rivalParty[i].getDisplayName());
            }
            System.out.println("]");
            GameUtils.pause(1500);

            // 배틀 시작
            BattleManager battle = new BattleManager(player, rival, scanner);
            battle.startBattle();

            // 재도전
            System.out.print("\n▶ 다시 도전하시겠습니까? (Y/N): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            playAgain = answer.equals("Y") || answer.equals("YES");
        }

        System.out.println("\n👋 다음에 또 만나요! 포켓몬 마스터!");
        scanner.close();
    }

    // 타이틀 출력
    private static void printTitle() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                                              ║");
        System.out.println("║     🎮 포켓몬 배틀 시뮬레이터 🎮              ║");
        System.out.println("║                                              ║");
        System.out.println("║     🔥 불  →  🌿 풀  →  💧 물  →  🔥 불     ║");
        System.out.println("║                                              ║");
        System.out.println("║     3 vs 3 턴제 배틀!                         ║");
        System.out.println("║                                              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }

    // 플레이어 포켓몬 선택
    private static Pokemon[] selectPlayerParty(Pokemon[] pokedex) {
        System.out.println("📋 포켓몬을 3마리 선택하세요!\n");
        PokemonFactory.printPokedex(pokedex);
        System.out.println();

        Pokemon[] party = new Pokemon[3];
        boolean[] selected = new boolean[pokedex.length];

        for (int i = 0; i < 3; i++) {
            while (true) {
                String[] ordinal = {"첫", "두", "세"};
                System.out.printf("▶ %s 번째 포켓몬 번호 (1~%d): ", ordinal[i], pokedex.length);
                int choice = GameUtils.getValidInput(1, pokedex.length);

                if (selected[choice - 1]) {
                    System.out.println("  ❌ 이미 선택한 포켓몬입니다. 다른 포켓몬을 선택하세요.");
                    continue;
                }

                selected[choice - 1] = true;
                party[i] = PokemonFactory.clonePokemon(pokedex[choice - 1]);
                System.out.println("  ✅ " + party[i].getDisplayName() + " 선택 완료!");
                break;
            }
        }

        System.out.print("\n✅ 선택 완료! [");
        for (int i = 0; i < party.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(party[i].getDisplayName());
        }
        System.out.println("]");

        return party;
    }

    // 라이벌 포켓몬 랜덤 배정
    private static Pokemon[] selectRivalParty(Pokemon[] pokedex) {
        Pokemon[] party = new Pokemon[3];

        // 각 속성에서 1마리씩 선택 (밸런스 있는 파티)
        int[] typeOrder = GameUtils.getRandomOrder(3);

        // 불 타입 중 랜덤 (인덱스 0,1)
        int fireChoice = (int) (Math.random() * 2);
        // 물 타입 중 랜덤 (인덱스 2,3)
        int waterChoice = 2 + (int) (Math.random() * 2);
        // 풀 타입 중 랜덤 (인덱스 4,5)
        int grassChoice = 4 + (int) (Math.random() * 2);

        int[] choices = {fireChoice, waterChoice, grassChoice};

        // 랜덤 순서로 배치
        for (int i = 0; i < 3; i++) {
            party[i] = PokemonFactory.clonePokemon(pokedex[choices[typeOrder[i]]]);
        }

        return party;
    }
}
