package ua.training.model.dao.mapper;

import ua.training.model.dto.ExhibitDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static ua.training.Constants.*;

public class ExhibitTradeMapper implements ObjectMapper<ExhibitDTO> {

    @Override
    public ExhibitDTO extractFromResultSet(ResultSet rs) throws SQLException {
        ExhibitDTO exhibitDTO = new ExhibitDTO();
        exhibitDTO.setId(rs.getLong(FIELD_ID));
        exhibitDTO.setName(rs.getString(FIELD_DB_NAME));
        exhibitDTO.setHallName(rs.getString(FIELD_DB_HALL_NAME));
        exhibitDTO.setStartDateTime(rs.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class));
        exhibitDTO.setEndDateTime(rs.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class));
        exhibitDTO.setMaxVisitorsPerDay(rs.getInt(FIELD_DB_MAX_VISITORS_PER_DAY));
        exhibitDTO.setTicketCost(rs.getLong(FIELD_DB_TICKET_COST));
        exhibitDTO.setHallId(rs.getLong(FIELD_DB_HALL_ID));

        return exhibitDTO;
    }
}
