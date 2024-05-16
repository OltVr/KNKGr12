package App;

import model.User;

public class SessionManager {
    private static User currentUser;

    public static void setUser(User user){
        currentUser=user;
    }

    public static User getUser(){
        return currentUser;
    }

    public static String getUserName(){
        if (currentUser != null){
            return currentUser.getFirstName();
        }else {
            return null;
        }
    }

    public static String getUserLastName(){
        if (currentUser != null){
            return currentUser.getLastName();
        }else{
            return null;
        }
    }

    public static String getUserId(){
        if(currentUser != null){
            return currentUser.getEmail();
        }else {
            return null;
        }
    }
}
