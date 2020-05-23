package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.model.service.HallService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class ExhibitAddCommand implements Command {
    private static final Logger log = Logger.getLogger(ExhibitAddCommand.class);

    private final HallService hallService;

    public ExhibitAddCommand(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            request.setAttribute(FIELD_HALLS, hallService.findAllHall());
            return EXHIBIT_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
