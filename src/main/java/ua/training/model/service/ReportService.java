package ua.training.model.service;

import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.AdminStatisticDTO;
import ua.training.model.dto.ReportDTO;
import ua.training.model.dto.UserStatisticDTO;
import ua.training.util.FinancialUtil;

public class ReportService {
    public ReportDTO<UserStatisticDTO> getUserReport(Long userId, int pageNumber) {
        ReportDTO<UserStatisticDTO> result;
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            result = ticketDao.getUserStatistic(userId, pageNumber);
        }

        result.getItems().forEach(c -> c.setPaidSum(FinancialUtil.calcCost(c.getPaidSum())));
        return result;
    }

    public ReportDTO<AdminStatisticDTO> getAdminReport(int pageNumber) {
        ReportDTO<AdminStatisticDTO> result;
        try (PaymentDao ticketDao = JDBCDaoFactory.getInstance().createPaymentDao()) {
            result = ticketDao.getAdminStatistic(pageNumber);
        }

        result.getItems().forEach(c -> c.setPaidSum(FinancialUtil.calcCost(c.getPaidSum())));
        return result;
    }
}


