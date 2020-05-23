package ua.training.model.dao;

import ua.training.model.entity.Payment;

public interface PaymentDao extends GenericDao<Payment> {
    void makePayment(Payment payment) throws Exception;
}
