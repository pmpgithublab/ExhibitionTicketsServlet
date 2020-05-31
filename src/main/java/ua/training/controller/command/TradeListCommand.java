package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.model.service.ExhibitService;
import ua.training.util.CheckUtils;
import ua.training.util.FinancialUtil;
import ua.training.util.MessageUtil;
import ua.training.model.dto.ExhibitDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.training.Constants.*;

public class TradeListCommand implements Command {
    private static final Logger log = Logger.getLogger(TradeListCommand.class);
    private static final String EXHIBITS_LIST_TRADE_PAGE = "/WEB-INF/trade/exhibits_list.jsp";
    private static final String CREATING_EXHIBIT_LIST_BY_DATES_ERROR = "Creating exhibit list by dates error";

    private final ExhibitService exhibitService;


    public TradeListCommand(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            String stringExhibitId = request.getParameter(FIELD_ID);
            List<ExhibitDTO> exhibitDTOS = exhibitService.findCurrentExhibits();

            request.setAttribute(EXHIBIT_THEMES_LIST, exhibitDTOS);

            if (CheckUtils.isPositiveLong(stringExhibitId)) {
                Long id = Long.parseLong(stringExhibitId);

                setupSelectedTheme(request, exhibitDTOS, id);
                try {
                    createExhibitList(request, id);
                } catch (CloneNotSupportedException e) {
                    log.error(CREATING_EXHIBIT_LIST_BY_DATES_ERROR + SLASH_SYMBOL + e.getMessage());
                    return REDIRECT_STRING + ERROR_PATH;
                }
            }

            return EXHIBITS_LIST_TRADE_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private void setupSelectedTheme(HttpServletRequest request, List<ExhibitDTO> exhibitDTOS, Long exhibitId) {
        request.setAttribute(EXHIBIT_SELECTED_THEME,
                exhibitDTOS.stream().filter(e -> e.getId().equals(exhibitId)).findFirst().get());
    }

    private void createExhibitList(HttpServletRequest request, Long exhibitId) throws CloneNotSupportedException {
        List<ExhibitDTO> exhibitListByDates = exhibitService.getExhibitListByDates(exhibitId);
        exhibitListByDates.forEach(c -> c.setTicketCost(FinancialUtil.calcCost(c.getTicketCost())));
        request.setAttribute(EXHIBIT_DATES_LIST, exhibitListByDates);
    }
}
