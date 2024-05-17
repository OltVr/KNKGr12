package model.dto;

public class InsertStaffDto {
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private double salary;
    private boolean isFullTime;
    private boolean hasBenefits;

    public InsertStaffDto(String firstName, String lastName, String email, String position, double salary, boolean isFullTime, boolean hasBenefits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.salary = salary;
        this.isFullTime = isFullTime;
        this.hasBenefits = hasBenefits;
    }

    public String getStaffFirstName() {
        return firstName;
    }

    public String getStaffLastName() {
        return lastName;
    }

    public String getStaffEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public boolean isHasBenefits() {
        return hasBenefits;
    }
}
