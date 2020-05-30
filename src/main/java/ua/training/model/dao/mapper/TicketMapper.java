package ua.training.model.dao.mapper;

import ua.training.model.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class TicketMapper implements ObjectMapper<Ticket> {
    private static final String FIELD_DB_USER_ID = "user_id";
    private static final String FIELD_DB_PAYMENT_ID = "payment_id";


    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        return new Ticket.TicketBuilder()
                .id(rs.getLong(FIELD_ID))
                .exhibitDate(rs.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class))
                .ticketQuantity(rs.getInt(FIELD_DB_TICKET_QUANTITY))
                .ticketSum(rs.getLong(FIELD_DB_TICKET_SUM))
                .paymentId(rs.getLong(FIELD_DB_PAYMENT_ID))
                .exhibitId(rs.getLong(FIELD_DB_EXHIBIT_ID))
                .hallId(rs.getLong(FIELD_DB_HALL_ID))
                .userId(rs.getLong(FIELD_DB_USER_ID))
                .build();
    }
}
