package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.model.service.HallService;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class HallsListCommand implements Command {
    private static final Logger log = Logger.getLogger(HallsListCommand.class);
    private static final String HALLS_LIST_PAGE = "/WEB-INF/admin/halls_list.jsp";

    private final HallService hallService;


    public HallsListCommand(HallService hallService) {
        this.hallService = hallService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            request.setAttribute(HALLS_LIST, hallService.findAllHall());

            return HALLS_LIST_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
