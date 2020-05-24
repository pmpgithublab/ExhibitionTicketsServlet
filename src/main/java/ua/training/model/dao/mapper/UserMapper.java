package ua.training.model.dao.mapper;

import ua.training.model.entity.User;
import ua.training.model.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.*;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return new User.UserBuilder()
                .id(rs.getLong(FIELD_DB_ID))
                .email(rs.getString(FIELD_DB_EMAIL))
                .password(rs.getString(FIELD_DB_PASSWORD))
                .role(UserRole.valueOf(rs.getString(FIELD_DB_ROLE)))
                .name(rs.getString(FIELD_DB_NAME_EN))
                .nameUK(rs.getString(FIELD_DB_NAME_UK))
                .build();
    }
}
