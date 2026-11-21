package motorcycletaxi.model;

public class Passenger {
    private int id;
    private String fullName;
    private String username;
    private String password;
    private int age;                    // only asked when the passenger is a student
    private boolean student;
    private StudentLevel studentLevel;  // null when not a student

    public Passenger(int id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.student = false;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public StudentLevel getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(StudentLevel studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getPassengerType() {
        return student ? "Student" : "Adult";
    }
}