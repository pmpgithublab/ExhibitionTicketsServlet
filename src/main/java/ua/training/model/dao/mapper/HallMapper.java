package ua.training.model.dao.mapper;

import ua.training.model.entity.Hall;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.FIELD_DB_ID;
import static ua.training.Constants.FIELD_DB_NAME;

public class HallMapper implements ObjectMapper<Hall> {
    @Override
    public Hall extractFromResultSet(ResultSet rs) throws SQLException {
        Hall hall = new Hall();
        hall.setId(rs.getLong(FIELD_DB_ID));
        hall.setName(rs.getString(FIELD_DB_NAME));

        return hall;
    }
}
