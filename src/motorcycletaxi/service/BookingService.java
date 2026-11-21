package motorcycletaxi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import motorcycletaxi.Data.LocationData;
import motorcycletaxi.model.Booking;
import motorcycletaxi.model.BookingStatus;
import motorcycletaxi.model.Driver;
import motorcycletaxi.model.Location;
import motorcycletaxi.model.Passenger;
import motorcycletaxi.model.StudentLevel;

public class BookingService {
    private List<Passenger> passengers = new ArrayList<>();
    private List<Driver> drivers = LocationData.createDrivers();
    private List<Booking> bookings = new ArrayList<>();
    private int nextPassengerId = 1;
    private int nextBookingId = 1;

    // ---------------------------------------------------- register / login

    // Returns null if OK, or an error message.
    public String register(String fullName, String username, String password) {
        username = username.toLowerCase();
        if (fullName.isEmpty()) {
            return "Name cannot be empty.";
        }
        if (!username.matches("[a-z0-9_]{3,}")) {
            return "Username must be 3 or more letters/numbers with no spaces.";
        }
        for (Passenger p : passengers) {
            if (p.getUsername().equals(username)) {
                return "Username already taken.";
            }
        }
        if (password.length() < 4) {
            return "Password must be at least 4 characters.";
        }
        passengers.add(new Passenger(nextPassengerId++, fullName, username, password));
        return null;
    }

    // Returns the passenger, or null if the username/password is wrong.
    public Passenger login(String username, String password) {
        username = username.toLowerCase();
        for (Passenger p : passengers) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    // ---------------------------------------------------- age / student rule

    // Not a student          -> allowed (adult)
    // High school / college  -> allowed
    // Elementary             -> allowed only with a parent or guardian
    public boolean canRide(Passenger p, boolean guardianWithPassenger) {
        if (!p.isStudent()) {
            return true;
        }
        if (p.getStudentLevel() == StudentLevel.ELEMENTARY) {
            return guardianWithPassenger;
        }
        return true;
    }

    public String getRideType(Passenger p, boolean guardianWithPassenger) {
        if (!p.isStudent()) {
            return "Adult";
        }
        if (p.getStudentLevel() == StudentLevel.ELEMENTARY && guardianWithPassenger) {
            return "Student with guardian";
        }
        return "Student";
    }

    // ---------------------------------------------------- distance / drivers

    // Straight-line distance in km (haversine), rounded to 1 decimal.
    public double distanceKm(Location a, Location b) {
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double x = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double km = 12742 * Math.asin(Math.sqrt(x));
        return Math.round(km * 10.0) / 10.0;
    }

    // The closest drivers who are not on a ride.
    public List<Driver> getNearestDrivers(Location from, int limit) {
        List<Driver> free = new ArrayList<>();
        for (Driver d : drivers) {
            if (d.isAvailable()) {
                free.add(d);
            }
        }
        free.sort(Comparator.comparingDouble((Driver d) -> distanceKm(from, d.getBaseLocation())));
        if (free.size() > limit) {
            return new ArrayList<>(free.subList(0, limit));
        }
        return free;
    }

    // ---------------------------------------------------- bookings

    public Booking createBooking(Passenger p, Driver d, Location from, Location to, String rideType) {
        double km = distanceKm(from, to);
        LocalDateTime now = LocalDateTime.now();
        double fare = FareCalculator.computeFare(km, now.toLocalTime());
        String fareType = FareCalculator.getFareType(now.toLocalTime());

        Booking b = new Booking(nextBookingId++, p, d, from, to, km, fare, fareType,
                rideType, BookingStatus.ACCEPTED, now);
        bookings.add(b);
        d.setAvailable(false);          // driver is now busy
        return b;
    }

    // The passenger's current ride (status ACCEPTED), or null.
    public Booking getActiveBooking(Passenger p) {
        for (Booking b : bookings) {
            if (b.getPassenger() == p && b.getStatus() == BookingStatus.ACCEPTED) {
                return b;
            }
        }
        return null;
    }

    public List<Booking> getBookingsOf(Passenger p) {
        List<Booking> mine = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getPassenger() == p) {
                mine.add(b);
            }
        }
        return mine;
    }

    // Returns a message to show on screen.
    public String cancelBooking(Passenger p, int bookingId) {
        Booking found = null;
        for (Booking b : getBookingsOf(p)) {        // only the passenger's own bookings
            if (b.getId() == bookingId) {
                found = b;
            }
        }
        if (found == null) {
            return "That booking is not yours or does not exist.";
        }
        if (found.getStatus() != BookingStatus.ACCEPTED) {
            return "This ride is already " + found.getStatus().toString().toLowerCase() + ".";
        }
        found.setStatus(BookingStatus.CANCELLED);
        found.getDriver().setAvailable(true);       // free the driver
        return "Booking #" + found.getId() + " cancelled. The driver is available again.";
    }

    // Marks the active ride as completed and frees the driver.
    public boolean finishRide(Passenger p) {
        Booking active = getActiveBooking(p);
        if (active == null) {
            return false;
        }
        active.setStatus(BookingStatus.COMPLETED);
        active.getDriver().setAvailable(true);
        return true;
    }
}
