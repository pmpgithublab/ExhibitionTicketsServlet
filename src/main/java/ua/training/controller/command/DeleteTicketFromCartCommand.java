package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.service.CartService;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class DeleteTicketFromCartCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteTicketFromCartCommand.class);

    private final CartService cartService;

    public DeleteTicketFromCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_POST)) {
            String stringId = request.getParameter(FIELD_ID);
            String stringDate = request.getParameter(PARAM_DATE);
            String stringTicketQuantity = request.getParameter(PARAM_TICKET_QUANTITY);

            if (isParametersValid(stringId, stringDate, stringTicketQuantity)) {
                Long id = Long.parseLong(stringId);
                LocalDate date = LocalDate.parse(stringDate);
                int ticketQuantity = Integer.parseInt(stringTicketQuantity);

                try {
                    cartService.deleteTicketFromCart(id, date, ticketQuantity, ControllerUtil.getUserId(request));
                } catch (Exception e) {
                    log.error(MessageUtil.getRuntimeExceptionMessage(e));
                    return REDIRECT_STRING + ERROR_PATH;
                }

                return REDIRECT_STRING + TRADE_PATH + CART_PATH;
            }

            log.warn(MessageUtil.getInvalidParameterMessage(request));
            return REDIRECT_STRING + ERROR_PATH;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private boolean isParametersValid(String stringId, String stringDate, String stringTicketQuantity) {
        return CheckUtils.isPositiveLong(stringId)
                && CheckUtils.isDate(stringDate) && CheckUtils.isPositiveInteger(stringTicketQuantity);
    }
}
