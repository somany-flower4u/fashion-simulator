import java.util.Scanner;

public class ConsoleUtils {
    private static Scanner scanner;

    public ConsoleUtils(Scanner s) {
        scanner = s;
    }

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

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignore
        }
    }

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
