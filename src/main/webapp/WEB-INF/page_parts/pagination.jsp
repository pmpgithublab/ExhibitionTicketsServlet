<ul class="nav d-flex justify-content-center  align-items-center">
    <li class="nav-item <c:if test="${ReportDTOS.getCurrentPage() == 0}"> nav-link disabled m-0 p-0 </c:if>">
        <a class="nav-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=0">
            <fmt:message key="pagination.first"/>
        </a>
    </li>
    <li class="nav-item <c:if test="${ReportDTOS.getCurrentPage() == 0}"> nav-link disabled m-0 p-0 </c:if>">
        <a class="nav-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${(ReportDTOS.getCurrentPage() - 1 < 0 ? 0 : ReportDTOS.getCurrentPage() - 1)}">
            <fmt:message key="pagination.previous"/>
        </a>
    </li>
    <li class="nav-item <c:if test="${ReportDTOS.getCurrentPage() == ReportDTOS.getPageQuantity()}"> nav-link disabled m-0 p-0 </c:if>">
        <a class="nav-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${(ReportDTOS.getCurrentPage() + 1 > ReportDTOS.getPageQuantity() ? ReportDTOS.getPageQuantity() : ReportDTOS.getCurrentPage() + 1)}">
            <fmt:message key="pagination.next"/>
        </a>
    </li>
    <li class="nav-item <c:if test="${ReportDTOS.getCurrentPage() == ReportDTOS.getPageQuantity()}"> nav-link disabled m-0 p-0 </c:if>">
        <a class="nav-link" href="${pageContext.request.contextPath}${ReportDTOS.getPageNavigationString()}/report?page=${ReportDTOS.getPageQuantity()}">
            <fmt:message key="pagination.last"/>
        </a>
    </li>
</ul>