package motorcycletaxi;

import java.util.ArrayList;
import java.util.Scanner;

import motorcycletaxi.model.Passenger;

public class Main {

    // Class variables (above main method)
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Passenger> passengers = new ArrayList<>();
    private static int nextPassengerId = 1;

    public static void main(String[] args) {

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