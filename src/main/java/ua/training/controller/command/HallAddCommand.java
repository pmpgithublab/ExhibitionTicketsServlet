package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class HallAddCommand implements Command {
    private static final Logger log = Logger.getLogger(HallAddCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {

            return HALL_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
