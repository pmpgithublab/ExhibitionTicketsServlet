package ua.training.model.dao.mapper;

import ua.training.model.entity.User;
import ua.training.model.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.*;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(FIELD_DB_ID));
        user.setEmail(rs.getString(FIELD_DB_EMAIL));
        user.setPassword(rs.getString(FIELD_DB_PASSWORD));
        user.setRole(UserRole.valueOf(rs.getString(FIELD_DB_ROLE)));
        user.setName(rs.getString(FIELD_DB_NAME_EN));
        user.setNameUK(rs.getString(FIELD_DB_NAME_UK));

        return user;
    }
}
