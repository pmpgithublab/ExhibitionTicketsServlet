package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.dto.TicketDTO;
import ua.training.model.exception.ExpiredPaymentDataException;
import ua.training.model.exception.ExternalPaymentSystemRejectPaymentException;
import ua.training.model.exception.NotEnoughTicketsException;
import ua.training.model.service.PaymentService;
import ua.training.util.CheckUtils;
import ua.training.util.FinancialUtil;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static ua.training.Constants.*;

public class PaymentCommand implements Command {
    private static final Logger log = Logger.getLogger(PaymentCommand.class);
    private static final String PAYMENT_PAGE = "/WEB-INF/trade/payment.jsp";
    private static final String EMPTY_PAYMENT_ERROR = "isEmptyPaymentError";
    private static final String USER_ASK_ZERO_PAYMENT_SUM_OR_TICKET_QUANTITY = "User ask zero payment sum or ticket quantity";
    private static final String PAYMENT_SUCCESSFUL = "Payment successful. Sum: ";
    private static final String PAYMENT_SYSTEM_REJECT_PAYMENT = "isPaymentSystemRejectPayment";
    private static final String PAYMENT_DATA_NOT_ACTUAL_OR_CANNOT_BE_PAID = "isPaymentDataNotActualOrCannotBePaid";
    private static final String IS_NOT_ENOUGH_TICKETS = "isNotEnoughTickets";
    private static final String PAYMENT_DATA_NOT_ACTUAL = "Payment data not actual. User id: ";

    private final PaymentService paymentService;

    public PaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            return showPaymentData(request);
        }

        if (request.getMethod().equals(METHOD_POST)) {
            return executePayment(request);
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String showPaymentData(HttpServletRequest request) {
        Optional<TicketDTO> payment = paymentService.findSumAndQuantityNotPaidUserTickets(ControllerUtil.getUserId(request));
        payment.ifPresent(p -> request.setAttribute(TOTAL_SUM, p.getTicketSum()));
        payment.ifPresent(p -> request.setAttribute(TOTAL_QUANTITY, p.getTicketQuantity()));

        return PAYMENT_PAGE;
    }

    private String executePayment(HttpServletRequest request) {
        String stringTotalQuantity = request.getParameter(TOTAL_QUANTITY);
        String stringTotalSum = request.getParameter(TOTAL_SUM);
        String currencySign = request.getParameter(CURRENCY_SIGN);

        if (CheckUtils.isPositiveInteger(stringTotalQuantity) && CheckUtils.isPositiveLong(stringTotalSum)
                && (currencySign.equals(DOLLAR_SIGN) || currencySign.equals(HRYVNA_SIGN))) {

            return paymentProcess(request, stringTotalQuantity, stringTotalSum, currencySign);
        }

        log.warn(MessageUtil.getInvalidParameterMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String paymentProcess(HttpServletRequest request, String stringTotalQuantity,
                                  String stringTotalSum, String currencySign) {

        int totalQuantity = Integer.parseInt(stringTotalQuantity);
        long totalSum = FinancialUtil.getAccountingSum(Long.parseLong(stringTotalSum), currencySign);
        long userId = ControllerUtil.getUserId(request);

        if (totalQuantity == 0 || totalSum == 0) {
            log.warn(USER_ASK_ZERO_PAYMENT_SUM_OR_TICKET_QUANTITY);
            request.setAttribute(EMPTY_PAYMENT_ERROR, true);
            return showPaymentData(request);
        }

        try {
            paymentService.paymentProcess(totalQuantity, totalSum, ControllerUtil.getUserId(request));
            log.info(PAYMENT_SUCCESSFUL + totalSum + TICKET_QUANTITY + totalQuantity + SESSION_ID + userId);
            request.setAttribute(IS_SUCCESSFUL, true);
        } catch (ExternalPaymentSystemRejectPaymentException e) {
            log.info(EXTERNAL_PAYMENT_SYSTEM_NOT_CONFIRM_TEMPORARY_BLOCK_USER_SUM + totalSum + SLASH_SYMBOL + userId);
            request.setAttribute(PAYMENT_SYSTEM_REJECT_PAYMENT, true);
        } catch (ExpiredPaymentDataException e) {
            log.info(PAYMENT_DATA_NOT_ACTUAL + userId);
            request.setAttribute(PAYMENT_DATA_NOT_ACTUAL_OR_CANNOT_BE_PAID, true);
        } catch (NotEnoughTicketsException e) {
            log.info(PAYMENT_NOT_ENOUGH_TICKETS + userId);
            request.setAttribute(IS_NOT_ENOUGH_TICKETS, true);
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            return REDIRECT_STRING + ERROR_PATH;
        }

        return showPaymentData(request);
    }
}
