package motorcycletaxi.model;

import java.util.Objects;

public class Location {
    private String town;
    private String barangay;
    private double latitude;
    private double longitude;

    public Location(String town, String barangay, double latitude, double longitude) {
        this.town = town;
        this.barangay = barangay;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTown() {
        return town;
    }

    public String getBarangay() {
        return barangay;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Two locations are the same if town and barangay match.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location other = (Location) o;
        return town.equals(other.town) && barangay.equals(other.barangay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(town, barangay);
    }

    // Prints like: San Isidro, Saint Bernard
    @Override
    public String toString() {
        return barangay + ", " + town;
    }
}
