package ua.training.model.entity;

import ua.training.model.dto.UserDTO;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String nameUK;
    private String email;
    private String password;
    private UserRole role;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.nameUK = userDTO.getNameUK();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
    }

    public User() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUK() {
        return nameUK;
    }

    public void setNameUK(String nameUK) {
        this.nameUK = nameUK;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


//    public static class UserBuilder{
//        private User user;
//
//        public UserBuilder() {
//            this.user = new User();
//        }
//
//        public UserBuilder name(Long id){
//            user.setId(id);
//            return this;
//        }
//
//        public UserBuilder name(String name){
//            user.setName(name);
//            return this;
//        }
//
//        public UserBuilder nameUK(String name){
//            user.setNameUK(name);
//            return this;
//        }
//
//        public UserBuilder email(String email){
//            user.setEmail(email);
//            return this;
//        }
//
//        public UserBuilder password(String password){
//            user.setPassword(password);
//            return this;
//        }
//
//        public UserBuilder role(UserRole role){
//            user.setRole(role);
//            return this;
//        }
//
//        public User build(){
//            return user;
//        }
//    }
}
