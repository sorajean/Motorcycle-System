package motorcycletaxi;

import java.util.ArrayList;
import java.util.Scanner;

import motorcycletaxi.model.Passenger;
import motorcycletaxi.model.Ride;
import motorcycletaxi.model.Driver;

public class Main {

    // Class variables
   private static final Scanner scanner = new Scanner(System.in);

private static ArrayList<Passenger> passengers = new ArrayList<>();
private static int nextPassengerId = 1;

private static ArrayList<Ride> rides = new ArrayList<>();
private static int nextRideId = 1;

private static ArrayList<Driver> drivers = new ArrayList<>();
private static int nextDriverId = 1;

    public static void main(String[] args) {

        while (true) {
            System.out.println();
            System.out.println("=== MOTORCYCLE TAXI BOOKING SYSTEM ===");
           System.out.println("1. Login");
           System.out.println("2. Register Passenger");
          System.out.println("3. Register Driver");
           System.out.println("4. View Registered Drivers");
          System.out.println("0. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                break;

            } else if (choice.equals("1")) {
                loginPassenger();

            } else if (choice.equals("2")) {
                registerPassenger();

                } else if (choice.equals("3")) {
                registerDriver();

                } else if (choice.equals("4")) {
    viewDrivers();

            } else {
                System.out.println("Please choose 0, 1, 3, or 4.");
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
            System.out.println("3. View My Rides");
            System.out.println("4. Cancel a Ride");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
             bookRide(passenger);

            } else if (choice.equals("2")) {
             viewAccountInformation(passenger);

           } else if (choice.equals("3")) {
    viewMyRides(passenger);

} else if (choice.equals("4")) {
    cancelRide(passenger);

} else if (choice.equals("5")) {
    System.out.println("Logging out...");
    break;
            } else {
                System.out.println("Please choose 1, 2, , 3 , 4 or 5.");
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

System.out.print("Enter estimated distance in kilometers: ");

double distance;

try {
    distance = Double.parseDouble(scanner.nextLine().trim());

    if (distance <= 0) {
        System.out.println("Distance must be greater than zero.");
        return;
    }

} catch (NumberFormatException e) {
    System.out.println("Please enter a valid distance.");
    return;
}

// Find an available driver
Driver assignedDriver = findAvailableDriver();

if (assignedDriver == null) {
    System.out.println("Sorry, no available drivers at the moment.");
    return;
}

// Double-check driver availability
if (!assignedDriver.isAvailable()) {
    System.out.println("Selected driver is no longer available.");
    return;
}

    System.out.println("\nRide details:");
    System.out.println("Pickup: " + pickup);
    System.out.println("Destination: " + destination);
    

    Ride ride = new Ride(
    nextRideId,
    passenger.getId(),
    pickup,
    destination
    

    
);

// Assign selected driver to the ride
ride.setDriverId(assignedDriver.getId());

double fare = calculateFare(distance);
ride.setFare(fare);

rides.add(ride);

// Mark assigned driver as unavailable
assignedDriver.setAvailable(false);

nextRideId++;

System.out.println("Ride request created successfully!");
System.out.println("Your Ride ID: " + ride.getId());
System.out.println("Pickup: " + ride.getPickup());
System.out.println("Destination: " + ride.getDestination());
System.out.println("Distance: " + distance + " km");
System.out.printf("Estimated Fare: PHP %.2f%n", ride.getFare());
System.out.println("Status: " + ride.getStatus());
System.out.println("Assigned Driver: " + assignedDriver.getFullName());
System.out.println("Motorcycle Plate: " + assignedDriver.getMotorcyclePlate());
}

public static double calculateFare(double distance) {

    double baseFare = 30.00;
    double farePerKilometer = 15.00;

    return baseFare + (distance * farePerKilometer);
}


public static void viewMyRides(Passenger passenger) {

    System.out.println("\n=== MY RIDE BOOKINGS ===");

    boolean found = false;

    for (Ride ride : rides) {

        if (ride.getPassengerId() == passenger.getId()) {

            System.out.println("--------------------------");
            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Pickup: " + ride.getPickup());
            System.out.println("Destination: " + ride.getDestination());
           System.out.println("Booking Owner ID: " + ride.getPassengerId());
           System.out.println("Fare: PHP " + ride.getFare());
           System.out.println("Status: " + ride.getStatus());

           // Display assigned driver information
for (Driver driver : drivers) {

    if (driver.getId() == ride.getDriverId()) {

        System.out.println("Assigned Driver: " + driver.getFullName());
        System.out.println("Motorcycle Plate: " + driver.getMotorcyclePlate());

        break;
    }
}

            found = true;
        }
    }

    if (!found) {
        System.out.println("You have no ride bookings yet.");
    }
}

public static void cancelRide(Passenger passenger) {

    System.out.println("\n=== CANCEL RIDE ===");

    boolean found = false;

    for (Ride ride : rides) {

        if (ride.getPassengerId() == passenger.getId()
                && ride.getStatus().equals("BOOKED")) {

            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Pickup: " + ride.getPickup());
            System.out.println("Destination: " + ride.getDestination());
            System.out.println("--------------------------");

            found = true;
        }
    }

    if (!found) {
        System.out.println("You have no active bookings to cancel.");
        return;
    }

    System.out.print("Enter Ride ID to cancel: ");

    int rideId;

    try {
        rideId = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid Ride ID.");
        return;
    }

    for (Ride ride : rides) {

        if (ride.getId() == rideId
                && ride.getPassengerId() == passenger.getId()
                && ride.getStatus().equals("BOOKED")) {

            ride.setStatus("CANCELLED");

            System.out.println("Ride cancelled successfully!");
            return;
        }
    }

    System.out.println("Ride not found or cannot be cancelled.");
}

public static void registerDriver() {

    System.out.println("\n=== DRIVER REGISTRATION ===");

    System.out.print("Driver full name: ");
    String fullName = scanner.nextLine().trim();

    if (fullName.isEmpty()) {
    System.out.println("Driver name cannot be empty.");
    return;
}

    System.out.print("Motorcycle plate number: ");
    String motorcyclePlate = scanner.nextLine().trim();

    if (motorcyclePlate.isEmpty()) {
    System.out.println("Motorcycle plate number cannot be empty.");
    return;
}

   for (Driver d : drivers) {
    if (d.getMotorcyclePlate().equalsIgnoreCase(motorcyclePlate)) {
        System.out.println("Motorcycle plate number already registered.");
        return;
    }
}

    Driver driver = new Driver(
        nextDriverId,
        fullName,
        motorcyclePlate
    );

    drivers.add(driver);
    nextDriverId++;

    System.out.println("Driver registered successfully!");
}

public static void viewDrivers() {

    System.out.println("\n=== REGISTERED DRIVERS ===");

    if (drivers.isEmpty()) {
        System.out.println("No drivers registered yet.");
        return;
    }

    for (Driver driver : drivers) {

        System.out.println("--------------------------");
        System.out.println("Driver ID: " + driver.getId());
        System.out.println("Full Name: " + driver.getFullName());
        System.out.println("Motorcycle Plate: " + driver.getMotorcyclePlate());
        System.out.println("Availability: "
                + (driver.isAvailable() ? "Available" : "Unavailable"));
    }
}

// Find an available driver
public static Driver findAvailableDriver() {

    for (Driver driver : drivers) {

        if (driver.isAvailable()) {
            return driver;
        }
    }

    return null;
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