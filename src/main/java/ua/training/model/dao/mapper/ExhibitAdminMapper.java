package ua.training.model.dao.mapper;

import ua.training.model.entity.Exhibit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static ua.training.Constants.*;

public class ExhibitAdminMapper implements ObjectMapper<Exhibit> {

    @Override
    public Exhibit extractFromResultSet(ResultSet rs) throws SQLException {
        Exhibit exhibit = new Exhibit();
        exhibit.setId(rs.getLong(FIELD_DB_ID));
        exhibit.setName(rs.getString(FIELD_DB_NAME_EN));
        exhibit.setNameUK(rs.getString(FIELD_DB_NAME_UK));
        exhibit.setStartDateTime(rs.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class));
        exhibit.setEndDateTime(rs.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class));
        exhibit.setMaxVisitorsPerDay(rs.getInt(FIELD_DB_MAX_VISITORS_PER_DAY));
        exhibit.setTicketCost(rs.getLong(FIELD_DB_TICKET_COST));
        exhibit.setHallId(rs.getLong(FIELD_DB_HALL_ID));

        return exhibit;
    }
}
