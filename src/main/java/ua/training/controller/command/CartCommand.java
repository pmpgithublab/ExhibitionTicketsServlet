package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.dto.UserCartDTO;
import ua.training.model.service.CartService;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ua.training.Constants.*;

public class CartCommand implements Command {
    private static final Logger log = Logger.getLogger(CartCommand.class);
    private static final String CART_PAGE = "/WEB-INF/trade/cart.jsp";
    private static final String TICKET_ADDED_TO_CART = "Ticket added to cart. Date: ";
    private static final String EXHIBIT_ID = ". Exhibit id: ";
    private static final String PARAM_ID = "?id=";

    private final CartService cartService;

    public CartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = request.getServletPath().replaceAll(TRADE_PATH, "");
        if (request.getMethod().equals(METHOD_GET)) {
            if (path.equals(CART_PATH)) {
                return showCart(request);
            }
        }

        if (request.getMethod().equals(METHOD_POST)) {
            if (path.equals(TICKET_ADD_TO_CART_PATH)) {
                return addTicketToCart(request);
            }
            if (path.equals(TICKET_DELETE_FROM_CART_PATH)) {
                return deleteTicketFromCart(request);
            }
            if (path.equals(CLEAR_CART_PATH)) {
                return clearCart(request);
            }
        }
        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String showCart(HttpServletRequest request) {
        List<UserCartDTO> userCart = cartService.getUserCart(ControllerUtil.getUserId(request));
        request.setAttribute(CART, userCart);
        request.setAttribute(EXPIRED_TICKETS, userCart.stream()
                .filter(r -> !CheckUtils.isExhibitDateTimeActual(r.getExhibitDate(),
                        r.getStartDateTime().toLocalDate(),
                        r.getEndDateTime().toLocalDate(),
                        r.getEndDateTime().toLocalTime()))
                .collect(Collectors.toList()));
        request.setAttribute(NOT_ENOUGH_TICKETS, userCart.stream()
                .filter(r -> r.getUnsoldTicketsQuantity() - r.getTicketQuantity() < 0).collect(Collectors.toList()));
        request.setAttribute(TOTAL_SUM, userCart.stream()
                .map(UserCartDTO::getTicketSum).reduce(Long::sum).orElse(0L));
        request.setAttribute(TOTAL_QUANTITY, userCart.stream()
                .map(UserCartDTO::getTicketQuantity).reduce(Integer::sum).orElse(0));

        return CART_PAGE;
    }

    private String addTicketToCart(HttpServletRequest request) {
        String stringExhibitId = request.getParameter(FIELD_ID);
        String stringExhibitDate = request.getParameter(PARAM_DATE);
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

    private boolean isParametersValid(String stringExhibitId, String stringExhibitDate, String stringTicketQuantity) {
        return CheckUtils.isPositiveLong(stringExhibitId)
                && CheckUtils.isDate(stringExhibitDate)
                && CheckUtils.isTicketQuantityValid(stringTicketQuantity);
    }

    private String deleteTicketFromCart(HttpServletRequest request) {
        String stringExhibitId = request.getParameter(FIELD_ID);
        String stringExhibitDate = request.getParameter(PARAM_DATE);
        String stringTicketQuantity = request.getParameter(PARAM_TICKET_QUANTITY);

        if (isParametersValid(stringExhibitId, stringExhibitDate, stringTicketQuantity)) {
            Long exhibitId = Long.parseLong(stringExhibitId);
            LocalDate exhibitDate = LocalDate.parse(stringExhibitDate);
            int ticketQuantity = Integer.parseInt(stringTicketQuantity);

            try {
                cartService.deleteTicketFromCart(exhibitId, exhibitDate, ticketQuantity, ControllerUtil.getUserId(request));
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                return REDIRECT_STRING + ERROR_PATH;
            }

            return REDIRECT_STRING + TRADE_PATH + CART_PATH;
        }

        log.warn(MessageUtil.getInvalidParameterMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String clearCart(HttpServletRequest request) {
        try {
            cartService.clearCart(ControllerUtil.getUserId(request));
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            return REDIRECT_STRING + ERROR_PATH;
        }

        return REDIRECT_STRING + TRADE_PATH + CART_PATH;
    }
}
