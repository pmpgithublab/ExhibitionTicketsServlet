package ua.training.model.service;

import org.apache.log4j.Logger;
import ua.training.util.FinancialUtil;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.TicketDTO;
import ua.training.model.entity.Payment;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.ExternalPaymentSystemRejectPaymentException;

import java.time.LocalDate;
import java.util.Optional;

import static ua.training.Constants.*;

public class PaymentService {
    private static final Logger log = Logger.getLogger(PaymentService.class);

    private static final String ASK_EXTERNAL_PAYMENT_SYSTEM_TEMPORARY_BLOCK_USER_SUM =
            "Ask external payment system temporary block user sum: ";
    private static final String EXTERNAL_PAYMENT_SYSTEM_CONFIRM_TEMPORARY_BLOCK_USER_SUM =
            "External payment system confirm temporary block user sum: ";
    private static final String SEND_CONFIRM_PAYMENT_TO_EXTERNAL_PAYMENT_SYSTEM =
            "Send confirm payment to external payment system";


    public Optional<TicketDTO> findSumAndQuantityNotPaidUserTickets(Long userId) {
        Optional<Ticket> result;
        try (TicketDao ticketDao = JDBCDaoFactory.getInstance().createTicketDao()) {
            result = ticketDao.findSumAndQuantityNotPaidUserTickets(userId);
        }
        if (result.isPresent()) {
            result.get().setTicketSum(FinancialUtil.calcCost(result.get().getTicketSum()));
            return Optional.of(new TicketDTO(result.get()));
        }

        return Optional.empty();
    }

    public void paymentProcess(int totalQuantity, Long totalSum, Long userId) throws Exception {
        log.info(ASK_EXTERNAL_PAYMENT_SYSTEM_TEMPORARY_BLOCK_USER_SUM + totalSum + SLASH_SYMBOL +  userId);
        if (FinancialUtil.askExternalPaymentSystemTemporaryBlockUserSum(userId, totalSum, TEN_SECONDS)) {
            log.info(EXTERNAL_PAYMENT_SYSTEM_CONFIRM_TEMPORARY_BLOCK_USER_SUM + totalSum + SLASH_SYMBOL + userId);

            Payment payment = createPayment(totalQuantity, totalSum, userId);

            try (PaymentDao paymentDao = JDBCDaoFactory.getInstance().createPaymentDao()) {
                paymentDao.makePayment(payment);
            }
            log.info(SEND_CONFIRM_PAYMENT_TO_EXTERNAL_PAYMENT_SYSTEM + totalSum + SLASH_SYMBOL + userId);
            FinancialUtil.confirmPayment(userId, totalSum);
        } else {
            throw new ExternalPaymentSystemRejectPaymentException(EXTERNAL_PAYMENT_SYSTEM_NOT_CONFIRM_TEMPORARY_BLOCK_USER_SUM);
        }
    }

    private Payment createPayment(int ticketQuantity, Long paidSum, Long userId) {
        return new Payment.PaymentBuilder()
                .ticketQuantity(ticketQuantity)
                .paidSum(paidSum)
                .paidDate(LocalDate.now())
                .userId(userId)
                .build();
    }
}
