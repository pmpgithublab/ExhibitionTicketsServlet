package ua.training.model.dto;

import ua.training.model.entity.User;
import ua.training.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ua.training.Constants.*;

public class UserDTO implements HttpSessionBindingListener {
    private static final Map<UserDTO, HttpSession> loggedUsers = new ConcurrentHashMap<>();

    private Long id;
    private String name;
    private String nameUK;
    private String email;
    private String password;
    private UserRole role;

    public UserDTO(HttpServletRequest request) {
        this.name = request.getParameter(FIELD_NAME);
        this.nameUK = request.getParameter(FIELD_NAME_UK);
        this.email = request.getParameter(FIELD_EMAIL);
        this.password = request.getParameter(FIELD_PASSWORD);
        this.role = UserRole.ROLE_USER;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nameUK = user.getNameUK();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public UserDTO() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        synchronized (loggedUsers) {
            HttpSession session = loggedUsers.remove(this);
            if (session != null) {
                session.invalidate();
            }
            loggedUsers.put(this, event.getSession());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        loggedUsers.remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(email);
        return email.hashCode();
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
}
