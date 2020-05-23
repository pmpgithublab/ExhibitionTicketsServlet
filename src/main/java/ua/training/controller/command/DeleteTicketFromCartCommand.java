package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;
import ua.training.model.dto.UserDTO;
import ua.training.model.service.CartService;

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
            String stringDate = request.getParameter(FIELD_DATE);
            String stringTicketQuantity = request.getParameter(PARAM_TICKET_QUANTITY);

            if (CheckUtils.isPositiveLong(stringId)
                    && CheckUtils.isDateValid(stringDate) && CheckUtils.isPositiveInteger(stringTicketQuantity)) {

                Long id = Long.parseLong(stringId);
                LocalDate date = LocalDate.parse(stringDate);
                int ticketQuantity = Integer.parseInt(stringTicketQuantity);

                try {
                    cartService.deleteTicketFromCart(id, date, ticketQuantity, getUserId(request));
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

    private Long getUserId(HttpServletRequest request) {
        return ((UserDTO) request.getSession().getAttribute(USER)).getId();
    }
}
