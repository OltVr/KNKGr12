package model.dto;

public class LoginUserDto {
    private String email;
    private String password;
    private boolean isAdmin;

    public LoginUserDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
