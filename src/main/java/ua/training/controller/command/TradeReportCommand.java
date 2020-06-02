package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.controller.util.ControllerUtil;
import ua.training.model.dto.ReportDTO;
import ua.training.model.dto.UserStatisticDTO;
import ua.training.model.service.ReportService;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class TradeReportCommand implements Command {
    private static final Logger log = Logger.getLogger(TradeReportCommand.class);
    private static final String USER_STATISTIC_PAGE = "/WEB-INF/trade/report.jsp";

    private final ReportService reportService;


    public TradeReportCommand(ReportService reportService) {
        this.reportService = reportService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            String stringPageNumber = request.getParameter(PARAM_PAGE_NUMBER);
            int pageNumber = 0;

            if (CheckUtils.isPositiveInteger(stringPageNumber)) {
                pageNumber = Integer.parseInt(stringPageNumber);
            } else {
                return REDIRECT_STRING + TRADE_PATH + REPORT_PATH
                        + PARAM_PAGE_NUMBER_REDIRECT + FIRST_PAGE;
            }

            ReportDTO<UserStatisticDTO> userStatisticDTOS =
                    reportService.getUserReport(ControllerUtil.getUserId(request), pageNumber);

            if (userStatisticDTOS.getPageQuantity() < pageNumber) {
                return REDIRECT_STRING + TRADE_PATH + REPORT_PATH
                        + PARAM_PAGE_NUMBER_REDIRECT + userStatisticDTOS.getPageQuantity();
            }

            userStatisticDTOS.setPageNavigationString(TRADE_PATH);
            request.setAttribute(ReportDTOS, userStatisticDTOS);

            return USER_STATISTIC_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
