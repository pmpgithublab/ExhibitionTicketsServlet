package ua.training.model.dao;

import ua.training.model.entity.User;
import ua.training.model.exception.SuchEmailExistsException;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    void save(User user) throws SuchEmailExistsException;

    Optional<User> findByEmailAndPassword(String name, String password);
}
