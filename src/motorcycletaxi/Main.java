package motorcycletaxi;

import java.util.ArrayList;
import java.util.Scanner;

import motorcycletaxi.model.Passenger;
import motorcycletaxi.model.Ride;

public class Main {

    // Class variables
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Passenger> passengers = new ArrayList<>();
    private static int nextPassengerId = 1;
    private static ArrayList<Ride> rides = new ArrayList<>();
private static int nextRideId = 1;

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
                loginPassenger();

            } else if (choice.equals("2")) {
                registerPassenger();

            } else {
                System.out.println("Please choose 0, 1, or 2.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    // Passenger login method
    public static void loginPassenger() {

        System.out.println("\n=== PASSENGER LOGIN ===");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        for (Passenger p : passengers) {

            if (p.getUsername().equalsIgnoreCase(username)
                    && p.getPassword().equals(password)) {

                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + p.getFullName() + "!");

                passengerDashboard(p);
                return;
            }
        }

        System.out.println("Invalid username or password.");

        
    }

    // Passenger dashboard method
    public static void passengerDashboard(Passenger passenger) {

        
        while (true) {
            System.out.println();
            System.out.println("=== PASSENGER DASHBOARD ===");
            System.out.println("Welcome, " + passenger.getFullName() + "!");
            System.out.println("1. Book a Ride");
            System.out.println("2. View Account Information");
            System.out.println("3. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
             bookRide(passenger);

            } else if (choice.equals("2")) {
             viewAccountInformation(passenger);

            } else if (choice.equals("3")) {
                System.out.println("Logging out...");
                break;

            } else {
                System.out.println("Please choose 1, 2, or 3.");
            }
        }
    }

    public static void viewAccountInformation(Passenger passenger) {
    System.out.println("\n=== ACCOUNT INFORMATION ===");
    System.out.println("Passenger ID: " + passenger.getId());
    System.out.println("Full Name: " + passenger.getFullName());
    System.out.println("Username: " + passenger.getUsername());
}

public static void bookRide(Passenger passenger) {
    System.out.println("\n=== BOOK A RIDE ===");

    System.out.print("Enter pickup location: ");
String pickup = scanner.nextLine().trim();

if (pickup.isEmpty()) {
    System.out.println("Pickup location cannot be empty.");
    return;
}

System.out.print("Enter destination: ");
String destination = scanner.nextLine().trim();

if (destination.isEmpty()) {
    System.out.println("Destination cannot be empty.");
    return;
}

    System.out.println("\nRide details:");
    System.out.println("Pickup: " + pickup);
    System.out.println("Destination: " + destination);

    System.out.println("Ride request created!");
}

    // Passenger registration method
    public static void registerPassenger() {

        System.out.println("\n=== PASSENGER REGISTRATION ===");

        System.out.print("Full name: ");
        String fullName = scanner.nextLine().trim();

        if (fullName.isEmpty()) {
            System.out.println("Full name cannot be empty.");
            return;
        }

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        if (!username.matches("[a-zA-Z0-9_]{3,}")) {
            System.out.println("Username must contain at least 3 letters, numbers, or underscores.");
            return;
        }

        for (Passenger p : passengers) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters.");
            return;
        }

        Passenger passenger = new Passenger(
            nextPassengerId,
            fullName,
            username,
            password
        );

        passengers.add(passenger);
        nextPassengerId++;

        System.out.println("Registration successful!");
    }
}