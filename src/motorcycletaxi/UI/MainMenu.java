package motorcycletaxi.UI;

package motorcycletaxi.ui;

import java.time.LocalTime;
import java.util.List;

import motorcycletaxi.Data.LocationData;
import motorcycletaxi.model.Booking;
import motorcycletaxi.model.Driver;
import motorcycletaxi.model.Location;
import motorcycletaxi.model.Passenger;
import motorcycletaxi.model.StudentLevel;
import motorcycletaxi.service.BookingService;
import motorcycletaxi.service.FareCalculator;

public class MainMenu {
    private BookingService service = new BookingService();

    // ---------------------------------------------------- main menu

    public void start() {
        while (true) {
            printTitle("MOTORCYCLE TAXI BOOKING SYSTEM", 32);
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            int choice = InputHelper.readInt("Choose: ", 0, 2);

            if (choice == 0) {
                System.out.println("Goodbye!");
                return;
            } else if (choice == 1) {
                login();
            } else {
                register();
            }
        }
    }

    private void register() {
        printTitle("REGISTER", 32);
        String name = InputHelper.readText("Full name: ");
        String username = InputHelper.readText("Username: ");
        String password = InputHelper.readText("Password (min 4 characters): ");

        String error = service.register(name, username, password);
        if (error == null) {
            System.out.println("Account created. You can log in now.");
        } else {
            System.out.println(error);
        }
    }

    private void login() {
        printTitle("LOGIN", 32);
        String username = InputHelper.readText("Username: ");
        String password = InputHelper.readText("Password: ");

        Passenger me = service.login(username, password);
        if (me == null) {
            System.out.println("Wrong username or password.");
        } else {
            passengerMenu(me);
        }
    }

    // ---------------------------------------------------- passenger menu

    private void passengerMenu(Passenger me) {
        while (true) {
            printTitle("Passenger: " + me.getFullName(), 30);
            System.out.println("1. Book a ride");
            System.out.println("2. My bookings");
            System.out.println("3. Cancel a booking");
            System.out.println("4. Finish my ride");
            System.out.println("0. Logout");
            int choice = InputHelper.readInt("Choose: ", 0, 4);

            if (choice == 0) {
                return;
            } else if (choice == 1) {
                bookRide(me);
            } else if (choice == 2) {
                showMyBookings(me);
            } else if (choice == 3) {
                cancelBooking(me);
            } else {
                finishRide(me);
            }
        }
    }

    // ---------------------------------------------------- book a ride

    private void bookRide(Passenger me) {
        if (service.getActiveBooking(me) != null) {
            System.out.println("You already have an active ride. Finish it or cancel it first.");
            return;
        }

        // Step 1: passenger information (student / age / parent rule)
        printTitle("Step 1: Passenger information", 30);
        boolean student = InputHelper.readYesNo("Are you a student? (yes/no): ");
        boolean guardian = false;

        if (student) {
            int age = InputHelper.readInt("Enter your age: ", 1, 100,
                    "Please enter a valid age (1 to 100).");
            System.out.println("Student level:");
            System.out.println("1. Elementary");
            System.out.println("2. High school");
            System.out.println("3. College");
            int lvl = InputHelper.readInt("Choose: ", 1, 3);

            me.setStudent(true);
            me.setAge(age);
            if (lvl == 1) {
                me.setStudentLevel(StudentLevel.ELEMENTARY);
            } else if (lvl == 2) {
                me.setStudentLevel(StudentLevel.HIGH_SCHOOL);
            } else {
                me.setStudentLevel(StudentLevel.COLLEGE);
            }

            if (me.getStudentLevel() == StudentLevel.ELEMENTARY) {
                System.out.println("\n*** NOTICE ***");
                System.out.println("You need a parent or guardian with you to ride our motorcycle.");
                guardian = InputHelper.readYesNo("Is a parent/guardian riding with you? (yes/no): ");
                if (!service.canRide(me, guardian)) {
                    System.out.println("\nSorry, an elementary student cannot ride alone.");
                    System.out.println("Please ask a parent or guardian to come with you.");
                    return;
                }
                System.out.println("Okay. Ride type: Student with guardian.");
            }
        } else {
            me.setStudent(false);
            me.setStudentLevel(null);
            System.out.println("Passenger type: Adult. Continuing...");
        }
        String rideType = service.getRideType(me, guardian);

        // Step 2: where are you now?
        Location from = chooseLocation("Step 2: Where are you now?");
        if (from == null) {
            return;
        }

        // Step 3: where are you going?
        Location to;
        while (true) {
            to = chooseLocation("Step 3: Where are you going?");
            if (to == null) {
                return;
            }
            if (to.equals(from)) {
                System.out.println("Destination cannot be the same as your location.");
                continue;
            }
            break;
        }

        // Step 4: closest drivers
        List<Driver> drivers = service.getNearestDrivers(from, 5);
        if (drivers.isEmpty()) {
            System.out.println("Sorry, no driver is available right now. Please try again later.");
            return;
        }
        printTitle("Step 4: Drivers closest to " + from, 54);
        for (int i = 0; i < drivers.size(); i++) {
            Driver d = drivers.get(i);
            String away;
            if (d.getBaseLocation().equals(from)) {
                away = "same barangay as you";
            } else {
                away = service.distanceKm(from, d.getBaseLocation()) + " km away";
            }
            System.out.println((i + 1) + ". " + d.getName() + " (" + d.getPlateNumber() + ")");
            System.out.println("   Base: " + d.getBaseLocation() + " - " + away);
        }
        System.out.println("0. Cancel");
        int pick = InputHelper.readInt("Choose a driver: ", 0, drivers.size());
        if (pick == 0) {
            return;
        }
        Driver driver = drivers.get(pick - 1);

        // Step 5: summary and "book now?"
        double km = service.distanceKm(from, to);
        LocalTime now = LocalTime.now();
        double fare = FareCalculator.computeFare(km, now);
        String fareType = FareCalculator.getFareType(now);

        printTitle("Step 5: Ride summary", 30);
        System.out.println("Passenger : " + me.getFullName() + " (" + rideType + ")");
        System.out.println("From      : " + from);
        System.out.println("To        : " + to);
        System.out.println("Driver    : " + driver.getName() + " (" + driver.getPlateNumber() + ")");
        System.out.println("Distance  : " + km + " km (straight line)");
        System.out.printf("Fare      : P%.2f [%s]%n", fare, fareType);
        System.out.println();

        if (!InputHelper.readYesNo("Do you want to book now? (yes/no): ")) {
            System.out.println("Ride cancelled. Nothing was booked.");
            return;
        }

        Booking b = service.createBooking(me, driver, from, to, rideType);
        System.out.println("\nRide confirmed! Booking #" + b.getId() + ".");
        System.out.println(driver.getName() + " is on the way to " + from + ".");
    }

    // Asks town, then barangay. Returns null if the passenger cancels.
    private Location chooseLocation(String heading) {
        while (true) {
            printTitle(heading, 30);
            List<String> towns = LocationData.getTowns();
            System.out.println("Choose a town:");
            for (int i = 0; i < towns.size(); i++) {
                System.out.println((i + 1) + ". " + towns.get(i) + " ("
                        + LocationData.getBarangays(towns.get(i)).size() + " barangays)");
            }
            System.out.println("0. Cancel");
            int t = InputHelper.readInt("Town: ", 0, towns.size());
            if (t == 0) {
                return null;
            }

            String town = towns.get(t - 1);
            List<String> brgys = LocationData.getBarangays(town);
            System.out.println("\nBarangays of " + town + ":");
            int half = (brgys.size() + 1) / 2;
            for (int i = 0; i < half; i++) {
                String left = (i + 1) + ". " + brgys.get(i);
                String right = "";
                if (i + half < brgys.size()) {
                    right = (i + half + 1) + ". " + brgys.get(i + half);
                }
                System.out.printf("%-28s%s%n", left, right);
            }
            System.out.println("0. Choose another town");
            int b = InputHelper.readInt("Barangay number: ", 0, brgys.size());
            if (b != 0) {
                return LocationData.getLocation(town, brgys.get(b - 1));
            }
        }
    }

    // ---------------------------------------------------- my bookings / cancel / finish

    private void showMyBookings(Passenger me) {
        List<Booking> mine = service.getBookingsOf(me);
        if (mine.isEmpty()) {
            System.out.println("\nYou have no bookings yet.");
            return;
        }
        System.out.println();
        for (Booking b : mine) {
            printBooking(b);
        }
    }

    private void cancelBooking(Passenger me) {
        List<Booking> mine = service.getBookingsOf(me);
        if (mine.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }
        System.out.println();
        for (Booking b : mine) {
            printBooking(b);
        }
        int id = InputHelper.readInt("Booking ID to cancel (0 to go back): ", 0, Integer.MAX_VALUE,
                "Please enter a valid booking number.");
        if (id == 0) {
            return;
        }
        System.out.println(service.cancelBooking(me, id));
    }

    private void finishRide(Passenger me) {
        Booking active = service.getActiveBooking(me);
        if (active == null) {
            System.out.println("You have no active ride.");
            return;
        }
        System.out.println("Your current ride: #" + active.getId() + " "
                + active.getFrom() + " -> " + active.getTo());
        if (InputHelper.readYesNo("Have you arrived at your destination? (yes/no): ")) {
            service.finishRide(me);
            System.out.println("Ride completed. Thank you for riding with us!");
        } else {
            System.out.println("Okay. Your ride is still active.");
        }
    }

    // ---------------------------------------------------- helpers

    private void printBooking(Booking b) {
        System.out.printf("#%d  %s  P%.2f [%s]  %.1f km%n",
                b.getId(), b.getStatus(), b.getFare(), b.getFareType(), b.getDistanceKm());
        System.out.println("    Driver: " + b.getDriver().getName()
                + " (" + b.getDriver().getPlateNumber() + ")  |  " + b.getRideType());
        System.out.println("    " + b.getFrom() + "  ->  " + b.getTo());
        System.out.println("    " + b.getDateTimeText());
        System.out.println();
    }

    private void printTitle(String text, int width) {
        System.out.println();
        System.out.println("=".repeat(width));
        System.out.println(" " + text);
        System.out.println("=".repeat(width));
    }
}