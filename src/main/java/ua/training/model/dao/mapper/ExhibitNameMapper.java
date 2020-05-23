package ua.training.model.dao.mapper;

import ua.training.model.entity.Exhibit;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.FIELD_DB_NAME;
import static ua.training.Constants.FIELD_ID;

public class ExhibitNameMapper implements ObjectMapper<Exhibit> {

    @Override
    public Exhibit extractFromResultSet(ResultSet rs) throws SQLException {
        Exhibit exhibit = new Exhibit();
        exhibit.setId(rs.getLong(FIELD_ID));
        exhibit.setName(rs.getString(FIELD_DB_NAME));

        return exhibit;
    }
}
