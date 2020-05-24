package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.model.dto.validator.HallDTOValidator;
import ua.training.model.exception.NoDuplicationAllowedException;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;
import ua.training.model.dto.HallDTO;
import ua.training.model.service.HallService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static ua.training.Constants.*;

public class HallEditCommand implements Command {
    private static final Logger log = Logger.getLogger(HallEditCommand.class);

    private static final String HALL_PAGE = "/WEB-INF/admin/hall.jsp";

    private final HallService hallService;


    public HallEditCommand(HallService hallService) {
        this.hallService = hallService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            return getHallForEdit(request);
        }

        if (request.getMethod().equals(METHOD_POST)) {
            return saveHall(request);
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String getHallForEdit(HttpServletRequest request) {
        String stringHallId = request.getParameter(FIELD_ID);
        if (CheckUtils.isPositiveLong(stringHallId)) {
            Optional<HallDTO> hallDTO = hallService.findById(Long.parseLong(stringHallId));

            if (hallDTO.isPresent()) {
                request.setAttribute(HALL_DTO, hallDTO.get());

                return HALL_PAGE;
            } else {
                log.warn(MessageUtil.getObjectByIdNotFoundMessage(request, HALL_NAME));
                return REDIRECT_STRING + ERROR_PATH;
            }
        }
        return HALL_PAGE;
    }

    private String saveHall(HttpServletRequest request) {
        HallDTO hallDTO = new HallDTO(request);

        if (isHallDTOValid(hallDTO)) {
            try {
                hallService.saveHall(hallDTO);
                log.info(MessageUtil.getObjectSaveMessage(request, HALL_NAME,
                        hallDTO.getName() + SLASH_SYMBOL + hallDTO.getNameUK()));
            } catch (NoDuplicationAllowedException e) {
                log.error(MessageUtil.getObjectSaveErrorMessage(request, HALL_NAME, e));
                setupErrorHallPage(request, hallDTO);

                return HALL_PAGE;
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                return REDIRECT_STRING + ERROR_PATH;
            }

            return REDIRECT_STRING + ADMIN_PATH + HALLS_LIST_PATH;
        }
        log.error(MessageUtil.getObjectNotValid(request, HALL_NAME));
        setupErrorHallPage(request, hallDTO);

        return HALL_PAGE;
    }

    private boolean isHallDTOValid(HallDTO hallDTO) {
        return new HallDTOValidator().isValid(hallDTO);
    }

    private void setupErrorHallPage(HttpServletRequest request, HallDTO hallDTO) {
        request.setAttribute(IS_ERROR, true);
        request.setAttribute(HALL_DTO, hallDTO);
    }
}