package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.model.service.ExhibitService;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class ExhibitListCommand implements Command {
    private static final Logger log = Logger.getLogger(ExhibitListCommand.class);
    private static final String EXHIBITS_LIST_ADMIN_PAGE = "/WEB-INF/admin/exhibits_list.jsp";

    private final ExhibitService exhibitService;

    public ExhibitListCommand(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            request.setAttribute(EXHIBITS_LIST, exhibitService.findAllExhibit());

            return EXHIBITS_LIST_ADMIN_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
