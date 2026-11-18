
package motorcycletaxi.model;

public class Driver {

    private int id;
    private String fullName;
    private String motorcyclePlate;
    private boolean available;

    public Driver(int id, String fullName, String motorcyclePlate) {
        this.id = id;
        this.fullName = fullName;
        this.motorcyclePlate = motorcyclePlate;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMotorcyclePlate() {
        return motorcyclePlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
