<nav class="nav d-flex justify-content-center  align-items-center">
    <ul class="pagination">
        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() == 0}"> disabled </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=0"><fmt:message key="pagination.first"/></a></li>
        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() == 0}"> disabled </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${(ReportDTOS.getCurrentPage() - 1 < 0 ? 0 : ReportDTOS.getCurrentPage() - 1)}"><fmt:message key="pagination.previous"/></a></li>


        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() <= 1}"> d-none </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getCurrentPage() - 2}">${ReportDTOS.getCurrentPage() - 1}</a></li>
        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() <= 0}"> d-none </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getCurrentPage() - 1}">${ReportDTOS.getCurrentPage()}</a></li>

        <li class="page-item disabled"><a class="page-link">${ReportDTOS.getCurrentPage() + 1}</a></li>

        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() + 1 > ReportDTOS.getPageQuantity()}"> d-none </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getCurrentPage() + 1}">${ReportDTOS.getCurrentPage() + 2}</a></li>
        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() + 2 > ReportDTOS.getPageQuantity()}"> d-none </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getCurrentPage() + 2}">${ReportDTOS.getCurrentPage() + 3}</a></li>


        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() == ReportDTOS.getPageQuantity()}"> disabled </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${(ReportDTOS.getCurrentPage() + 1 > ReportDTOS.getPageQuantity() ? ReportDTOS.getPageQuantity() : ReportDTOS.getCurrentPage() + 1)}"><fmt:message key="pagination.next"/></a></li>
        <li class="page-item<c:if test="${ReportDTOS.getCurrentPage() == ReportDTOS.getPageQuantity()}"> disabled </c:if>"><a class="page-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getPageQuantity()}"><fmt:message key="pagination.last"/></a></li>
    </ul>
</nav>