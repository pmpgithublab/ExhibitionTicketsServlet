package ua.training.model.dao.mapper;

import ua.training.model.dto.UserStatisticDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class UserStatisticDTOMapper implements ObjectMapper<UserStatisticDTO> {

    @Override
    public UserStatisticDTO extractFromResultSet(ResultSet rs) throws SQLException {
        UserStatisticDTO userStatisticDTO = new UserStatisticDTO();
        userStatisticDTO.setExhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class));
        userStatisticDTO.setExhibitName(rs.getString(FIELD_DB_NAME));
        userStatisticDTO.setTicketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY));
        userStatisticDTO.setPaidSum(rs.getLong(FIELD_DB_TICKET_SUM));

        return userStatisticDTO;
    }
}
