package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;
import ua.training.model.dto.AdminStatisticDTO;
import ua.training.model.dto.ReportDTO;
import ua.training.model.service.ReportService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class AdminReportCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminReportCommand.class);
    private static final String ADMIN_STATISTIC_PAGE = "/WEB-INF/admin/admin_statistic.jsp";

    private final ReportService reportService;


    public AdminReportCommand(ReportService reportService) {
        this.reportService = reportService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            String stringPageNumber = request.getParameter(FIELD_PAGE);
            int pageNumber = 0;

            if (CheckUtils.isPositiveInteger(stringPageNumber)) {
                pageNumber = Integer.parseInt(stringPageNumber);
            }

            ReportDTO<AdminStatisticDTO> adminStatisticDTOS = reportService.getAdminPurchases(pageNumber);
            adminStatisticDTOS.setCurrentPage(pageNumber);
            adminStatisticDTOS.setPageNavigationString(ADMIN_PATH);
            request.setAttribute(ReportDTOS, adminStatisticDTOS);

            return ADMIN_STATISTIC_PAGE;
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }
}
