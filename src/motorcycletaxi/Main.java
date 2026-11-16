import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("=== MOTORCYCLE TAXI BOOKING SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                break;
            } else if (choice.equals("1")) {
                System.out.println("Login is coming soon.");
            } else if (choice.equals("2")) {
                System.out.println("Register is coming soon.");
            } else {
                System.out.println("Please choose 0, 1, or 2.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}