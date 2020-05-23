package ua.training.model.dao.mapper;

import ua.training.model.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class TicketMapper implements ObjectMapper<Ticket> {

    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong(FIELD_ID));
        ticket.setExhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class));
        ticket.setTicketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY));
        ticket.setTicketSum(rs.getLong(FIELD_DB_TICKET_SUM));
        ticket.setPaymentId(rs.getLong(FIELD_DB_PAYMENT_ID));
        ticket.setExhibitId(rs.getLong(FIELD_DB_EXHIBIT_ID));
        ticket.setHallId(rs.getLong(FIELD_DB_HALL_ID));
        ticket.setUserId(rs.getLong(FIELD_DB_USER_ID));

        return ticket;
    }
}
