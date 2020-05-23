package ua.training.model.service;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.UserDTO;
import ua.training.model.entity.User;
import ua.training.model.exception.SuchEmailExistsException;

import java.util.Optional;

public class UserService {

    public void saveUser(UserDTO userDTO) throws SuchEmailExistsException {
        try(UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            userDao.save(new User(userDTO));
        }
    }

    public Optional<UserDTO> findUserByEmailAndPassword(String email, String password) {
        try(UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            return userDao.findByEmailAndPassword(email, password).map(UserDTO::new);
        }
    }
}
