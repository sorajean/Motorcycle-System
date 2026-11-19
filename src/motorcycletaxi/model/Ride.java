package motorcycletaxi.model;

public class Ride {

    private int id;
    private int passengerId;
    private String pickup;
    private String destination;
    private String status;
    private double fare;
    private int driverId;

    public Ride(int id, int passengerId, String pickup, String destination) {
        this.id = id;
        this.passengerId = passengerId;
        this.pickup = pickup;
        this.destination = destination;
        this.status = "BOOKED";
    }

    public int getId() {
        return id;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;

        
    }
    public double getFare() {
    return fare;
}

public void setFare(double fare) {
    this.fare = fare;
}
}