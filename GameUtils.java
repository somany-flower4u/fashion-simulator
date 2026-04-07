import java.util.Scanner;

/**
 * 게임 전역 유틸리티 클래스
 * - 입력 검증
 * - 시간 지연
 */
public class GameUtils {
    private static Scanner scanner;

    public GameUtils(Scanner s) {
        scanner = s;
    }

    /**
     * 유효한 범위의 정수 입력 받기
     */
    public static int getValidInput(int min, int max) {
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

    /**
     * 지정된 밀리초만큼 일시정지
     */
    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // 무시
        }
    }

    /**
     * 무작위 순서 배열 생성 (Fisher-Yates shuffle)
     */
    public static int[] getRandomOrder(int n) {
        int[] order = new int[n];
        for (int i = 0; i < n; i++) order[i] = i;
        for (int i = n - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = order[i];
            order[i] = order[j];
            order[j] = temp;
        }
        return order;
    }
}
