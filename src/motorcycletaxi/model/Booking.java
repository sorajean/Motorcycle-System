package motorcycletaxi.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private int id;
    private Passenger passenger;
    private Driver driver;
    private Location from;
    private Location to;
    private double distanceKm;
    private double fare;
    private String fareType;     // Regular or Night
    private String rideType;     // Adult, Student, Student with guardian
    private BookingStatus status;
    private LocalDateTime dateTime;

    public Booking(int id, Passenger passenger, Driver driver, Location from, Location to,
                   double distanceKm, double fare, String fareType, String rideType,
                   BookingStatus status, LocalDateTime dateTime) {
        this.id = id;
        this.passenger = passenger;
        this.driver = driver;
        this.from = from;
        this.to = to;
        this.distanceKm = distanceKm;
        this.fare = fare;
        this.fareType = fareType;
        this.rideType = rideType;
        this.status = status;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getFare() {
        return fare;
    }

    public String getFareType() {
        return fareType;
    }

    public String getRideType() {
        return rideType;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeText() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}