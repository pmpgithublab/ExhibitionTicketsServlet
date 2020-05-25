package ua.training.model.dao;

import ua.training.model.dto.*;
import ua.training.model.entity.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TicketDao extends GenericDao<Ticket> {
    void save(Ticket ticket) throws Exception;

    Optional<Ticket> findTicketByExhibitIdAndExhibitDateAndUserIdAndNotPaid(Long exhibitId, LocalDate exhibitDate, Long userId);

    void deleteAllByUserIdAndNotPaid(Long userId) throws Exception;

    void deleteByIdAndNotPaid(Long userId) throws Exception;

    ReportDTO<UserStatisticDTO> getUserStatistic(Long userId, int pageNumber);

    ReportDTO<AdminStatisticDTO> getAdminStatistic(int pageNumber);

    Optional<Ticket> findByUserIdSumAndQuantityNotPaidTickets(Long userId);

    List<UserCartDTO> getUserCart(Long userId);
}
