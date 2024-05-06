package service;

import Repository.UserRepository;
import model.dto.CreateUserDto;
import model.dto.UserDto;

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
}
