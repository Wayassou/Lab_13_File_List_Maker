import java.util.Scanner;

public class SafeInput {

    private static Scanner in = new Scanner(System.in);

    public static String getRegExString(String prompt, String regEx) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = in.nextLine();
            if (input.matches(regEx)) {
                return input;
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    public static String getNonZeroLenString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = in.nextLine();
            if (input.length() > 0) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Try again.");
            }
        }
    }

    public static int getRangedInt(String prompt, int low, int high) {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt + " ");
            if (in.hasNextInt()) {
                input = in.nextInt();
                in.nextLine(); // Consume newline
                if (input >= low && input <= high) {
                    valid = true;
                } else {
                    System.out.println("Input out of range. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                in.nextLine(); // Clear invalid input
            }
        }
        return input;
    }

    public static boolean getYNConfirm(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " [Y/N]: ");
            input = in.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }
}

