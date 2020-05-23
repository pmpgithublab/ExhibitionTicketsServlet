package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.service.ExhibitService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.training.Constants.*;

public class ExhibitListCommand implements Command {
    private static final Logger log = Logger.getLogger(ExhibitListCommand.class);

    private static final String EXHIBIT_EDIT_REST = "/admin/exhibit_edit?id=";

    private final ExhibitService exhibitService;

    public ExhibitListCommand(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            List<ExhibitDTO> allExhibits = exhibitService.findAllExhibit();
            allExhibits.forEach(s -> s.setRestLink(request.getContextPath() + EXHIBIT_EDIT_REST + s.getId()));
            request.setAttribute(EXHIBITS_LIST, allExhibits);

            return EXHIBITS_LIST_ADMIN_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
