package motorcycletaxi.model;

public class Driver {
    private int id;
    private String name;
    private String plateNumber;
    private Location baseLocation;
    private boolean available;

    public Driver(int id, String name, String plateNumber, Location baseLocation) {
        this.id = id;
        this.name = name;
        this.plateNumber = plateNumber;
        this.baseLocation = baseLocation;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}