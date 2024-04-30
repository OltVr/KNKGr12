package model.dto;

public class LoginUserdto {
        private String email;
        private String password;

        public LoginUserdto(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

