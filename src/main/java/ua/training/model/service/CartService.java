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

public class CartService {
    private static final Logger log = Logger.getLogger(CartService.class);
    private static final String TICKET_CREATION_OR_SAVING_ERROR = "Ticket creation or saving error. User id: ";


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
                Optional<TicketDTO> newTicket = createTicketByExhibitIdAndExhibitDate(exhibitId, exhibitDate, ticketQuantity, userId);
                if (newTicket.isPresent()) {
                    saveTicket(newTicket.get());
                } else {
                    log.error(MessageUtil.getRuntimeExceptionMessage(TICKET_CREATION_OR_SAVING_ERROR + userId));
                    throw new RuntimeException(TICKET_CREATION_OR_SAVING_ERROR + userId);
                }
            }
        }
    }

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

    public void saveTicket(TicketDTO ticketDTO) throws Exception {
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            ticketDao.save(new Ticket(ticketDTO));
        }
    }

    public void clearCart(Long userId) throws Exception {
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            ticketDao.deleteAllByUserIdAndNotPaid(userId);
        }
    }

    public List<UserCartDTO> getUserCart(Long userId) {
        List<UserCartDTO> result;
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            result = ticketDao.getUserCart(userId);
        }

        result.forEach(c -> c.setTicketSum(FinancialUtil.calcCost(c.getTicketSum())));
        return result;
    }
}
