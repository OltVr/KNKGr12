package service;



import javafx.collections.ObservableList;
import model.Room;
import model.User;
import model.dto.LoginUserDto;
import Repository.UserRepository;
import model.dto.CreateUserDto;
import model.dto.UserDto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserService {
    public static boolean signUp(UserDto userData){
        String password=userData.getPassword();
        String confirmPassword=userData.getConfirmPassword();

        if(!password.equals(confirmPassword)){
            return false;
        }
        String salt= PasswordHasher.generateSalt();
        String passwordHash=PasswordHasher.generateSaltedHash(password, salt);

        CreateUserDto createUserData = new CreateUserDto(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                salt,
                passwordHash
        );
    return UserRepository.create(createUserData);
    }

    public static boolean login( LoginUserDto loginData){
        User user = UserRepository.getByEmail(loginData.getEmail());
        if(user == null){
            return false;
        }

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getPasswordHash();


        return PasswordHasher.compareSaltedHash(
                password, salt, passwordHash
        );
    }

    public static boolean loginAdmin( LoginUserDto loginData){
        if (login(loginData)){
            User user = UserRepository.getByEmail(loginData.getEmail());
            if(user == null){
                return false;
            }
            return user.isAdmin();
        }
        return false;
    }

    public static User getUserEmail(String email){
        return UserRepository.getByEmail(email);
    }

    public static ObservableList<Room> listSeaViewRooms() {
        return UserRepository.listSeaViewRooms();
    }

    public static ObservableList<Room> listCityViewRooms() {
        return UserRepository.listCityViewRooms();
    }

    //TODO: Check if this code is where it should be. We may need to create a reserve service
    public static double totalPrice(LocalDate start, LocalDate end, double price){
        long days= ChronoUnit.DAYS.between(start,end);
        return days*price;
    }
}
