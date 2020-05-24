package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.dto.TicketDTO;
import ua.training.model.dto.UserCartDTO;
import ua.training.model.service.CartService;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static ua.training.Constants.*;

public class ShowCartCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowCartCommand.class);
    private static final String CART_PAGE = "/WEB-INF/trade/cart.jsp";

    private final CartService cartService;

    public ShowCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
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

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
