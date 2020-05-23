package ua.training.model.dao;

import ua.training.model.dto.AdminStatisticDTO;
import ua.training.model.dto.ReportDTO;
import ua.training.model.dto.TicketDTO;
import ua.training.model.dto.UserStatisticDTO;
import ua.training.model.entity.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TicketDao extends GenericDao<Ticket> {
    void save(Ticket ticket) throws Exception;

    List<TicketDTO> findAllNotPaidUserTickets(Long userId);

    Optional<Ticket> findTicketByExhibitIdAndExhibitDateAndUserIdAndNotPaid(Long exhibitId, LocalDate exhibitDate, Long userId);

    void deleteAllNotPaid(Long userId) throws Exception;

    void deleteByIdNotPaid(Long userId) throws Exception;

    ReportDTO<UserStatisticDTO> getUserStatistic(Long userId, int pageNumber);

    ReportDTO<AdminStatisticDTO> getAdminStatistic(int pageNumber);

    Optional<Ticket> findSumAndQuantityNotPaidUserTickets(Long userId);
}
