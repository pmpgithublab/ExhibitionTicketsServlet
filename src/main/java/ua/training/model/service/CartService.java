package ua.training.model.service;

import org.apache.log4j.Logger;
import ua.training.model.dao.ExhibitDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.TicketDTO;
import ua.training.model.dto.UserCartDTO;
import ua.training.model.entity.Ticket;
import ua.training.util.FinancialUtil;
import ua.training.util.MessageUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * A service for work with user cart.
 * Can create, store, change and delete a ticket.
 * Also can show a list of user tickets in the user cart.
 */
public class CartService {
    private static final Logger log = Logger.getLogger(CartService.class);
    /**
     * use for error logging during to current exhibit list building
     */
    private static final String TICKET_CREATION_OR_SAVING_ERROR = "Ticket creation or saving error. User id: ";


    /**
     * Checks if a ticket with required exhibit id and date exists at the user.
     * If exists adds tickets quantity and sum to exists ticket and save it to the database.
     * If not exists, then creates a new one and saves it to the database.
     *
     * @param exhibitId      An identifier of the required exhibit
     * @param exhibitDate    A ticket date of the exhibit
     * @param ticketQuantity Quantity tickets to the exhibit
     * @param userId         The identifier of the user to whose cart the ticket is added
     * @throws Exception If adding is failed
     */
    public void addTicketToCart(Long exhibitId, LocalDate exhibitDate, int ticketQuantity, Long userId)
            throws Exception {

        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            Optional<Ticket> ticketFromDB =
                    ticketDao.findTicketByExhibitIdAndExhibitDateAndUserIdAndNotPaid(exhibitId, exhibitDate, userId);
            if (ticketFromDB.isPresent()) {
                long cost = ticketFromDB.get().getTicketSum() / ticketFromDB.get().getTicketQuantity();
                ticketFromDB.get().setTicketQuantity(ticketFromDB.get().getTicketQuantity() + ticketQuantity);
                ticketFromDB.get().setTicketSum(ticketFromDB.get().getTicketQuantity() * cost);
                saveTicket(new TicketDTO(ticketFromDB.get()));
            } else {
                Optional<TicketDTO> newTicket =
                        createTicketByExhibitIdAndExhibitDate(exhibitId, exhibitDate, ticketQuantity, userId);
                if (newTicket.isPresent()) {
                    saveTicket(newTicket.get());
                } else {
                    log.error(MessageUtil.getRuntimeExceptionMessage(TICKET_CREATION_OR_SAVING_ERROR + userId));
                    throw new RuntimeException(TICKET_CREATION_OR_SAVING_ERROR + userId);
                }
            }
        }
    }

    /**
     * Create a new ticketDTO object. Reads actual an exhibit and a hall data from DB.
     *
     * @param exhibitId      An identifier of the required exhibit
     * @param exhibitDate    A ticket date of the exhibit
     * @param ticketQuantity Quantity tickets to the exhibit
     * @param userId         The identifier of the user who creates the ticket
     * @return An Optional with the TicketDTO value present or an empty Optional
     */
    public Optional<TicketDTO> createTicketByExhibitIdAndExhibitDate(Long exhibitId, LocalDate exhibitDate,
                                                                     int ticketQuantity, Long userId) {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            return exhibitDao.findByIdWithHall(exhibitId)
                    .map(result -> new TicketDTO.TicketDTOBuilder()
                            .exhibitDate(exhibitDate)
                            .exhibitId(result.getId())
                            .exhibitName(result.getName())
                            .hallId(result.getHallId())
                            .hallName(result.getHallName())
                            .ticketQuantity(ticketQuantity)
                            .ticketSum(ticketQuantity * result.getTicketCost())
                            .userId(userId)
                            .build());
        }
    }

    /**
     * Checks if a ticket with required exhibit id and date exists at the user. If exists subtracts tickets
     * quantity and sum from exists ticket if sum and quantity less than exists and save it to the database.
     * If exists and quantity and sum are equals or more than exists, then deletes ticket from the database.
     * If not exists does nothing.
     *
     * @param exhibitId      An identifier of the required exhibit
     * @param exhibitDate    A ticket date of the exhibit
     * @param ticketQuantity Quantity tickets to the exhibit
     * @param userId         The identifier of the user to whose cart the ticket is added
     * @throws Exception If deleting is failed
     */
    public void deleteTicketFromCart(Long exhibitId, LocalDate exhibitDate, int ticketQuantity, Long userId)
            throws Exception {
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            Optional<Ticket> ticketFromDB =
                    ticketDao.findTicketByExhibitIdAndExhibitDateAndUserIdAndNotPaid(exhibitId, exhibitDate, userId);
            if (ticketFromDB.isPresent()) {
                if (ticketFromDB.get().getTicketQuantity() > ticketQuantity) {
                    addTicketToCart(exhibitId, exhibitDate, -ticketQuantity, userId);
                } else {
                    ticketDao.deleteByIdAndNotPaid(ticketFromDB.get().getId());
                }
            }
        }
    }

    /**
     * Save a ticket to the database.
     *
     * @param ticketDTO The ticket which is saved to the database
     * @throws Exception If saving is failed
     */
    public void saveTicket(TicketDTO ticketDTO) throws Exception {
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            ticketDao.save(new Ticket(ticketDTO));
        }
    }

    /**
     * Deletes all unpaid user tickets from the database.
     *
     * @param userId The identifier of the user whose tickets are deleted from the cart
     * @throws Exception If deletion is failed
     */
    public void clearCart(Long userId) throws Exception {
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            ticketDao.deleteAllByUserIdAndNotPaid(userId);
        }
    }

    /**
     * Reads unpaid user tickets from the database.
     *
     * @param userId The identifier of the user whose cart is read
     * @return A List of UserCartDTO with user-selected and not paid tickets (otherwise an empty)
     */
    public List<UserCartDTO> getUserCart(Long userId) {
        List<UserCartDTO> result;
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            result = ticketDao.getUserCart(userId);
        }

        result.forEach(c -> c.setTicketSum(FinancialUtil.calcCost(c.getTicketSum())));
        return result;
    }
}