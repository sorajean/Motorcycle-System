package motorcycletaxi.model;

public class Passenger {
    private int id;
    private String fullName;
    private String username;
    private String password;

    public Passenger(int id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

        public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
