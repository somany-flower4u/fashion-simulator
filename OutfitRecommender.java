import java.util.Scanner;

public class OutfitRecommender {
    private Wardrobe wardrobe;
    private RecommendStrategy strategy;
    private Scanner scanner;

    public OutfitRecommender(Wardrobe wardrobe, RecommendStrategy strategy, Scanner scanner) {
        this.wardrobe = wardrobe;
        this.strategy = strategy;
        this.scanner = scanner;
    }

    public void startRecommendation() {
        printIntro();
        Situation situation = inputSituation();

        System.out.println("\n🔍 최적의 코디를 분석 중입니다...");
        ConsoleUtils.pause(1000);

        Outfit recommended = strategy.recommend(wardrobe, situation);
        int score = recommended.calculateTotalScore(situation);

        printResult(recommended, situation, score);
    }

    private void printIntro() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║         👔 코디 추천 시뮬레이터 👔          ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    private Situation inputSituation() {
        System.out.println("\n📍 오늘의 상황을 입력하세요");

        System.out.println("\n▶ 날씨를 선택하세요");
        Weather[] weathers = Weather.values();
        for (int i = 0; i < weathers.length; i++) {
            System.out.printf("  %d. %s %s\n", i + 1, weathers[i].getEmoji(), weathers[i].getKorName());
        }
        System.out.print("▶ 선택: ");
        int wChoice = ConsoleUtils.getValidInput(1, weathers.length);

        System.out.println("\n▶ 일정을 선택하세요");
        Schedule[] schedules = Schedule.values();
        for (int i = 0; i < schedules.length; i++) {
            System.out.printf("  %d. %s %s\n", i + 1, schedules[i].getEmoji(), schedules[i].getKorName());
        }
        System.out.print("▶ 선택: ");
        int sChoice = ConsoleUtils.getValidInput(1, schedules.length);

        System.out.print("\n▶ 오늘 기온은 몇 도인가요? (-10 ~ 40): ");
        int temp = ConsoleUtils.getValidInput(-10, 40);

        return new Situation(weathers[wChoice - 1], schedules[sChoice - 1], temp);
    }

    private void printResult(Outfit outfit, Situation situation, int score) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  ✅ 추천 코디가 완성되었습니다!");
        System.out.println("═══════════════════════════════════════");
        System.out.println("\n📅 오늘의 상황: " + situation.getDisplay());
        System.out.println("\n👔 추천 코디:");
        outfit.printOutfit();
        System.out.println("\n📊 적합도 점수: " + score + " / 1200");

        if (score >= 900) {
            System.out.println("  🌟 완벽한 코디! 오늘 하루 자신감 넘치게!");
        } else if (score >= 700) {
            System.out.println("  👍 훌륭한 코디! 오늘 상황에 딱 맞습니다.");
        } else if (score >= 500) {
            System.out.println("  😊 묘난한 코디입니다. 크게 틀리지 않아요.");
        } else {
            System.out.println("  🤔 조금 아쉬울 수 있는 코디... 옷장을 다양화해 보세요.");
        }

        System.out.println("═══════════════════════════════════════");
    }
}
