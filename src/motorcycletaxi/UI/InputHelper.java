package motorcycletaxi.UI;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Keeps asking until the user types a whole number from min to max.
    public static int readInt(String prompt, int min, int max, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n >= min && n <= max) {
                    return n;
                }
            } catch (NumberFormatException e) {
                // not a number: fall through to the error message
            }
            System.out.println(errorMessage);
        }
    }

    public static int readInt(String prompt, int min, int max) {
        return readInt(prompt, min, max, "Please enter a number from " + min + " to " + max + ".");
    }

    // Keeps asking until the user types yes or no.
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes")) {
                return true;
            }
            if (answer.equals("no")) {
                return false;
            }
            System.out.println("Please type yes or no.");
        }
    }
}