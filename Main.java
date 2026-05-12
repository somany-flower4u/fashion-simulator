import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printTitle();

        boolean repeat = true;
        while (repeat) {
            ClothingItem[] tops = Wardrobe.Factory.createAllTops();
            ClothingItem[] bottoms = Wardrobe.Factory.createAllBottoms();
            ClothingItem[] outers = Wardrobe.Factory.createAllOuters();

            Wardrobe wardrobe = new Wardrobe("사용자", tops, bottoms, outers);

            RecommendStrategy strategy = new RecommendStrategy.Basic();
            OutfitRecommender recommender = new OutfitRecommender(wardrobe, strategy, scanner);
            recommender.printWardrobe();
            recommender.startRecommendation();

            System.out.print("\n▶ 다른 상황으로 다시 추천받으시겠습니까? (Y/N): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            repeat = answer.equals("Y") || answer.equals("YES");
        }

        System.out.println("\n👋 다음에 또 이용해주세요! 스타일리시한 하루 되세요!");
        scanner.close();
    }

    private static void printTitle() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                                              ║");
        System.out.println("║   👔 날씨 및 목적 기반 외출 코디네이터 👔             ║");
        System.out.println("║                                              ║");
        System.out.println("║   매일 아침 날씨와 일정에 맞춰                      ║");
        System.out.println("║   최적의 코디를 추천해드립니다!                     ║");
        System.out.println("║                                              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }
}
