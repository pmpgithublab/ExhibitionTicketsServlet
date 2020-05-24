package ua.training.model.dao.mapper;

import ua.training.model.entity.Exhibit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static ua.training.Constants.*;

public class ExhibitAdminMapper implements ObjectMapper<Exhibit> {

    @Override
    public Exhibit extractFromResultSet(ResultSet rs) throws SQLException {
        return new Exhibit.ExhibitBuilder()
                .id(rs.getLong(FIELD_DB_ID))
                .name(rs.getString(FIELD_DB_NAME_EN))
                .nameUK(rs.getString(FIELD_DB_NAME_UK))
                .startDateTime(rs.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class))
                .endDateTime(rs.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class))
                .maxVisitorsPerDay(rs.getInt(FIELD_DB_MAX_VISITORS_PER_DAY))
                .ticketCost(rs.getLong(FIELD_DB_TICKET_COST))
                .hallId(rs.getLong(FIELD_DB_HALL_ID))
                .build();
    }
}
