package ua.training.model.dao;

import ua.training.model.dto.AdminStatisticDTO;
import ua.training.model.dto.ReportDTO;
import ua.training.model.entity.Payment;

public interface PaymentDao extends GenericDao<Payment> {
    void makePayment(Payment payment) throws Exception;

    ReportDTO<AdminStatisticDTO> getAdminStatistic(int pageNumber);
}
