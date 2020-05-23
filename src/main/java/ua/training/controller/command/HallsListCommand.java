package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.model.dto.HallDTO;
import ua.training.model.service.HallService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.training.Constants.*;

public class HallsListCommand implements Command {
    private static final Logger log = Logger.getLogger(HallsListCommand.class);

    private static final String HALL_EDIT_REST = "/admin/hall_edit?id=";

    private final HallService hallService;


    public HallsListCommand(HallService hallService) {
        this.hallService = hallService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            List<HallDTO> allHalls = hallService.findAllHall();
            allHalls.forEach(s -> s.setRestLink(request.getContextPath() + HALL_EDIT_REST + s.getId()));
            request.setAttribute(HALLS_LIST, allHalls);

            return HALLS_LIST_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
