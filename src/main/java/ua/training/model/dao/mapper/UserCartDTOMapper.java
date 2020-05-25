package ua.training.model.dao.mapper;

import ua.training.model.dto.UserCartDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static ua.training.Constants.*;

public class UserCartDTOMapper implements ObjectMapper<UserCartDTO> {
    private static final String FIELD_DB_EXHIBIT_NAME = "exhibit_name";

    @Override
    public UserCartDTO extractFromResultSet(ResultSet rs) throws SQLException {
        return new UserCartDTO.UserCartDTOBuilder()
                .ticketId(rs.getLong(FIELD_DB_ID))
                .exhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class))
                .exhibitId(rs.getLong(FIELD_DB_EXHIBIT_ID))
                .exhibitName(rs.getString(FIELD_DB_EXHIBIT_NAME))
                .hallName(rs.getString(FIELD_DB_HALL_NAME))
                .ticketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY))
                .ticketSum(rs.getLong(FIELD_DB_TICKET_SUM))
                .startDateTime(rs.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class))
                .endDateTime(rs.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class))
                .unsoldTicketsQuantity(rs.getInt(FIELD_DB_REMAIN_TICKETS))
                .build();
    }
}
