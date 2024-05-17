package model;

import java.util.Date;

public class Staff {

    private int staffID;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private double salary;
    private boolean isEmployed;
    private boolean isFullTime;
    private boolean hasBenefits;
    private Date createdAt;

    public Staff(int staffID, String firstName,String lastName, String email, String position, double salary, boolean isActive, boolean isFullTime, boolean hasBenefits, Date createdAt) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.salary = salary;
        this.isEmployed = isActive;
        this.isFullTime = isFullTime;
        this.hasBenefits = hasBenefits;
        this.createdAt = createdAt;
    }

    public int getStaffID() {
        return staffID;
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

    public boolean isEmployed() {
        return isEmployed;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public boolean isHasBenefits() {
        return hasBenefits;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
