package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.service.CartService;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static ua.training.Constants.*;

public class AddTicketToCartCommand implements Command {
    private static final Logger log = Logger.getLogger(AddTicketToCartCommand.class);
    private static final String TICKET_ADDED_TO_CART = "Ticket added to cart. Date: ";
    private static final String EXHIBIT_ID = ". Exhibit id: ";
    private static final String PARAM_ID = "?id=";

    private final CartService cartService;

    public AddTicketToCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_POST)) {
            String stringExhibitId = request.getParameter(FIELD_ID);
            String stringExhibitDate = request.getParameter(FIELD_DATE);
            String stringTicketQuantity = request.getParameter(PARAM_TICKET_QUANTITY);

            if (isParametersValid(stringExhibitId, stringExhibitDate, stringTicketQuantity)) {
                Long exhibitId = Long.parseLong(stringExhibitId);
                LocalDate exhibitDate = LocalDate.parse(stringExhibitDate);
                int ticketQuantity = Integer.parseInt(stringTicketQuantity);

                try {
                    cartService.addTicketToCart(exhibitId, exhibitDate, ticketQuantity, ControllerUtil.getUserId(request));
                    log.info(TICKET_ADDED_TO_CART + stringExhibitDate
                            + EXHIBIT_ID + stringExhibitId + TICKET_QUANTITY + stringTicketQuantity);
                } catch (Exception e) {
                    log.error(MessageUtil.getRuntimeExceptionMessage(e));
                    return REDIRECT_STRING + ERROR_PATH;
                }

                return REDIRECT_STRING + TRADE_PATH + EXHIBITS_LIST_PATH + PARAM_ID + stringExhibitId;
            }

            log.warn(MessageUtil.getInvalidParameterMessage(request));
            return REDIRECT_STRING + ERROR_PATH;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private boolean isParametersValid(String stringExhibitId, String stringExhibitDate, String stringTicketQuantity) {
        return CheckUtils.isPositiveLong(stringExhibitId)
                && CheckUtils.isDate(stringExhibitDate)
                && CheckUtils.isTicketQuantityValid(stringTicketQuantity);
    }
}
