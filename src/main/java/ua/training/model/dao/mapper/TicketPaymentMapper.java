package ua.training.model.dao.mapper;

import ua.training.model.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.Constants.FIELD_DB_TICKET_QUANTITY;
import static ua.training.Constants.FIELD_DB_TICKET_SUM;

public class TicketPaymentMapper implements ObjectMapper<Ticket> {

    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY));
        ticket.setTicketSum(rs.getLong(FIELD_DB_TICKET_SUM));

        return ticket;
    }
}
