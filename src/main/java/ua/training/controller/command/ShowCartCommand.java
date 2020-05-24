package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.Util;
import ua.training.model.dto.TicketDTO;
import ua.training.model.service.CartService;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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


            // todo fill expired and lack lists with data
//            UserCartDTO = cartService.getUserCart();
//            request.setAttribute(CART, UserCartDTO.getAllTickets());
//            request.setAttribute(EXPIRED_TICKETS, UserCartDTO.getExpiredTickets());
//            request.setAttribute(NOT_ENOUGH_TICKETS, UserCartDTO.getLackTickets());
//            request.setAttribute(TOTAL_SUM, UserCartDTO.getAllTickets().stream()
//                    .map(TicketDTO::getTicketSum).reduce(Long::sum).orElse(0L));
//            request.setAttribute(TOTAL_QUANTITY, UserCartDTO.getAllTickets().stream()
//                    .map(TicketDTO::getTicketQuantity).reduce(Integer::sum).orElse(0));

            List<TicketDTO> ticketDTOS = cartService.findAllNotPaidUserTickets(Util.getUserId(request));
            request.setAttribute(CART, ticketDTOS);
            request.setAttribute(EXPIRED_TICKETS, new ArrayList<>());     // todo remove
            request.setAttribute(NOT_ENOUGH_TICKETS, new ArrayList<>());  // todo remove


            request.setAttribute(TOTAL_SUM, ticketDTOS.stream()
                    .map(TicketDTO::getTicketSum).reduce(Long::sum).orElse(0L));
            request.setAttribute(TOTAL_QUANTITY, ticketDTOS.stream()
                    .map(TicketDTO::getTicketQuantity).reduce(Integer::sum).orElse(0));

            return CART_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
