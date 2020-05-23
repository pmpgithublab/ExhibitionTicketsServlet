package ua.training.model.dao.mapper;

import ua.training.model.dto.AdminStatisticDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.*;

public class AdminStatisticDTOMapper implements ObjectMapper<AdminStatisticDTO> {

    @Override
    public AdminStatisticDTO extractFromResultSet(ResultSet rs) throws SQLException {
        AdminStatisticDTO adminStatisticDTO = new AdminStatisticDTO();
        adminStatisticDTO.setUserName(rs.getString(FIELD_DB_NAME));
        adminStatisticDTO.setTicketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY));
        adminStatisticDTO.setPaidSum(rs.getLong(FIELD_DB_TICKET_SUM));

        return adminStatisticDTO;
    }
}
