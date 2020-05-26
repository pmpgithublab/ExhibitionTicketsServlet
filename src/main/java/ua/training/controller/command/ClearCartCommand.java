package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.service.CartService;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class ClearCartCommand implements Command {
    private static final Logger log = Logger.getLogger(ClearCartCommand.class);

    private final CartService cartService;

    public ClearCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_POST)) {
            try {
                cartService.clearCart(ControllerUtil.getUserId(request));
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                return REDIRECT_STRING + ERROR_PATH;
            }

            return REDIRECT_STRING + TRADE_PATH + CART_PATH;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
