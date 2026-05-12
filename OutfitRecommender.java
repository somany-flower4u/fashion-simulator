import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class OutfitRecommender {
    private Wardrobe wardrobe;
    private RecommendStrategy strategy;
    private Scanner scanner;

    private static final List<GradeThreshold> GRADES = Arrays.asList(
        new GradeThreshold(900, "🌟 완벽한 코디! 오늘 하루 자신감 넘치게!"),
        new GradeThreshold(700, "👍 훌륭한 코디! 오늘 상황에 딱 맞습니다."),
        new GradeThreshold(500, "😊 무난한 코디입니다. 크게 틀리지 않아요."),
        new GradeThreshold(0,   "🤔 조금 아쉬울 수 있는 코디... 옷장을 다양화해 보세요.")
    );

    public OutfitRecommender(Wardrobe wardrobe, RecommendStrategy strategy, Scanner scanner) {
        this.wardrobe = wardrobe;
        this.strategy = strategy;
        this.scanner = scanner;
    }

    public void startRecommendation() {
        printIntro();
        Situation situation = inputSituation();

        System.out.println("\n🔍 최적의 코디를 분석 중입니다...");
        pause(1000);

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
        int wChoice = getValidInput(1, weathers.length);

        System.out.println("\n▶ 일정을 선택하세요");
        Schedule[] schedules = Schedule.values();
        for (int i = 0; i < schedules.length; i++) {
            System.out.printf("  %d. %s %s\n", i + 1, schedules[i].getEmoji(), schedules[i].getKorName());
        }
        System.out.print("▶ 선택: ");
        int sChoice = getValidInput(1, schedules.length);

        System.out.print("\n▶ 오늘 기온은 몇 도인가요? (-10 ~ 40): ");
        int temp = getValidInput(-10, 40);

        return new Situation(weathers[wChoice - 1], schedules[sChoice - 1], temp);
    }

    private void printResult(Outfit outfit, Situation situation, int score) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  ✅ 추천 코디가 완성되었습니다!");
        System.out.println("═══════════════════════════════════════");
        System.out.println("\n📅 오늘의 상황: " + situation.getDisplay());
        System.out.println("\n👔 추천 코디:");
        System.out.println("  👕 상의: " + outfit.getTop().getDisplayName());
        System.out.println("  👖 하의: " + outfit.getBottom().getDisplayName());
        System.out.println("  🧥 아우터: " + outfit.getOuter().getDisplayName());
        System.out.println("\n📊 적합도 점수: " + score + " / 1200");

        String feedback = getFeedback(score);
        System.out.println("  " + feedback);

        System.out.println("═══════════════════════════════════════");
    }

    private String getFeedback(int score) {
        for (GradeThreshold grade : GRADES) {
            if (score >= grade.threshold) {
                return grade.message;
            }
        }
        return "";
    }

    public void printWardrobe() {
        System.out.println("\n📋 " + wardrobe.getOwnerName() + "의 옷장");
        System.out.println("┌─────────┬──────────┬──────┬──────┬──────┬──────┐");
        System.out.println("│ 카테고리 │ 이름      │ 보온  │ 방수  │ 격식  │ 편안  │");
        System.out.println("├─────────┼──────────┼──────┼──────┼──────┼──────┤");
        for (ClothingItem c : wardrobe.getTops()) printItem(c);
        for (ClothingItem c : wardrobe.getBottoms()) printItem(c);
        for (ClothingItem c : wardrobe.getOuters()) printItem(c);
        System.out.println("└─────────┴──────────┴──────┴──────┴──────┴──────┘");
    }

    private void printItem(ClothingItem c) {
        System.out.printf("│   %s   │ %-6s  │ %-3d │ %-3d │ %-3d │ %-3d │\n",
            c.getCategory().getEmoji(), c.getName(),
            c.getWarmth(), c.getWaterResistance(), c.getFormality(), c.getComfort());
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
                System.out.printf("  ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("  ❌ %d~%d 사이의 숫자를 입력하세요: ", min, max);
            }
        }
    }

    private void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static class GradeThreshold {
        final int threshold;
        final String message;

        GradeThreshold(int threshold, String message) {
            this.threshold = threshold;
            this.message = message;
        }
    }
}
