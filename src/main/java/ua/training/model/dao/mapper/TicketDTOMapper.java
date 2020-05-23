package ua.training.model.dao.mapper;

import ua.training.model.dto.TicketDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class TicketDTOMapper implements ObjectMapper<TicketDTO> {

    @Override
    public TicketDTO extractFromResultSet(ResultSet rs) throws SQLException {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(rs.getLong(FIELD_ID));
//        ticketDTO.setExhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class));
//        ticketDTO.setTicketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY));
//        ticketDTO.setTicketSum(rs.getLong(FIELD_DB_TICKET_SUM));
//        ticketDTO.setPaymentId(rs.getLong(FIELD_DB_PAYMENT_ID));
//        ticketDTO.setExhibitId(rs.getLong(FIELD_DB_EXHIBIT_ID));
//        ticketDTO.setExhibitName(rs.getString(FIELD_DB_EXHIBIT_NAME));
//        ticketDTO.setHallId(rs.getLong(FIELD_DB_HALL_ID));
//        ticketDTO.setHallName(rs.getString(FIELD_DB_HALL_NAME));
//        ticketDTO.setUserId(rs.getLong(FIELD_DB_USER_ID));

//        return ticketDTO;

        return new TicketDTO.TicketDTOBuilder()
                .id(rs.getLong(FIELD_ID))
                .exhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class))
                .ticketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY))
                .ticketSum(rs.getLong(FIELD_DB_TICKET_SUM))
                .paymentId(rs.getLong(FIELD_DB_PAYMENT_ID))
                .exhibitId(rs.getLong(FIELD_DB_EXHIBIT_ID))
                .exhibitName(rs.getString(FIELD_DB_EXHIBIT_NAME))
                .hallId(rs.getLong(FIELD_DB_HALL_ID))
                .hallName(rs.getString(FIELD_DB_HALL_NAME))
                .userId(rs.getLong(FIELD_DB_USER_ID))
                .build();
    }
}
