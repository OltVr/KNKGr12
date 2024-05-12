package model;

import java.sql.Timestamp;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private String passwordHash;
    private boolean isAdmin;
    private Timestamp CreatedAt;

    public User( String firstName, String lastName, String email, String salt, String passwordHash, boolean isAdmin, Timestamp CreatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
        this.CreatedAt=CreatedAt;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }
}
