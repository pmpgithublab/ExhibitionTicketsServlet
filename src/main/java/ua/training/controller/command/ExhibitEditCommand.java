package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.model.dto.validator.ExhibitDTOValidator;
import ua.training.model.exception.NoDuplicationAllowedException;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.dto.HallDTO;
import ua.training.model.service.ExhibitService;
import ua.training.model.service.HallService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static ua.training.Constants.*;

public class ExhibitEditCommand implements Command {
    private static final Logger log = Logger.getLogger(ExhibitEditCommand.class);
    private static final String EXHIBIT_PAGE = "/WEB-INF/admin/exhibit.jsp";
    private static final String EXHIBIT_DTO = "exhibitDTO";
    private static final String FIELD_SELECTED_HALL = "selectedHall";
    private static final String FIELD_HALLS = "halls";

    private final HallService hallService;
    private final ExhibitService exhibitService;

    public ExhibitEditCommand(HallService hallService, ExhibitService exhibitService) {
        this.hallService = hallService;
        this.exhibitService = exhibitService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            return getExhibitForEdit(request);
        }

        if (request.getMethod().equals(METHOD_POST)) {
            return saveExhibit(request);
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String getExhibitForEdit(HttpServletRequest request) {
        String stringExhibitId = request.getParameter(FIELD_ID);
        if (CheckUtils.isPositiveLong(stringExhibitId)) {
            Optional<ExhibitDTO> exhibitDTOFromDB = exhibitService.findById(Long.parseLong(stringExhibitId));

            if (exhibitDTOFromDB.isPresent()) {
                setupExhibitPage(request, exhibitDTOFromDB.get());

                return EXHIBIT_PAGE;
            } else {
                log.warn(MessageUtil.getObjectByIdNotFoundMessage(request, EXHIBIT_NAME));
                return REDIRECT_STRING + ERROR_PATH;
            }
        }

        request.setAttribute(FIELD_HALLS, hallService.findAllHall());
        return EXHIBIT_PAGE;
    }

    private String saveExhibit(HttpServletRequest request) {
        ExhibitDTO exhibitDTO = new ExhibitDTO(request);
        if (isExhibitDTOValid(exhibitDTO)) {
            try {
                exhibitService.saveExhibit(exhibitDTO);
                log.info(MessageUtil.getObjectSaveMessage(request, EXHIBIT_NAME,
                        exhibitDTO.getName() + SLASH_SYMBOL + exhibitDTO.getNameUK()));
            } catch (NoDuplicationAllowedException e) {
                log.error(MessageUtil.getObjectSaveErrorMessage(request, EXHIBIT_NAME, e));
                request.setAttribute(IS_ERROR, true);
                setupExhibitPage(request, exhibitDTO);

                return EXHIBIT_PAGE;
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                return REDIRECT_STRING + ERROR_PATH;
            }

            return REDIRECT_STRING + ADMIN_PATH + EXHIBITS_LIST_PATH;
        }
        log.error(MessageUtil.getObjectNotValid(request, EXHIBIT_NAME));
        request.setAttribute(IS_ERROR, true);
        setupExhibitPage(request, exhibitDTO);

        return EXHIBIT_PAGE;
    }

    private boolean isExhibitDTOValid(ExhibitDTO exhibitDTO) {
        return new ExhibitDTOValidator().isValid(exhibitDTO);
    }

    private void setupExhibitPage(HttpServletRequest request, ExhibitDTO exhibitDTO) {
        request.setAttribute(EXHIBIT_DTO, exhibitDTO);
        List<HallDTO> hallDTOS = hallService.findAllHall();
        request.setAttribute(FIELD_HALLS, hallDTOS);
        request.setAttribute(FIELD_SELECTED_HALL,
                hallDTOS.stream().filter(e -> e.getId().equals(exhibitDTO.getHallId())).findFirst().get());
    }
}
