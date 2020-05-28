<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.exhibits"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.exhibits"/></h3><br>

<table class="table">
    <thead>
    <tr>
        <th style="width: 10%"></th>
        <th style="width: 30%"><fmt:message key="theme"/></th>
        <th style="width: 16%"><fmt:message key="startDateTime"/></th>
        <th style="width: 16%"><fmt:message key="endDateTime"/></th>
        <th class="text-right" style="width: 6%"><fmt:message key="maxVisitorsPerDay"/></th>
        <th class="text-right" style="width: 6%"><fmt:message key="cost"/></th>
        <th style="width: 6%"><fmt:message key="edit"/></th>
        <th style="width: 10%"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${exhibitsList}" var="exhibitItem">
        <fmt:parseDate value="${exhibitItem.startDateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="startDateTime"
                       type="both"/>
        <fmt:parseDate value="${exhibitItem.endDateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="endDateTime"
                       type="both"/>
        <tr>
            <td></td>
            <td>${exhibitItem.name}</td>
            <c:choose>
                <c:when test="${language != 'en'}">
                    <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${startDateTime}"/></td>
                    <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${endDateTime}"/></td>
                </c:when>
                <c:otherwise>
                    <td><fmt:formatDate pattern="MMM dd yyyy h:mm a" value="${startDateTime}"/></td>
                    <td><fmt:formatDate pattern="MMM dd yyyy h:mm a" value="${endDateTime}"/></td>
                </c:otherwise>
            </c:choose>
            <td class="text-right pr-2">${exhibitItem.maxVisitorsPerDay}</td>
            <td class="text-right pr-2">
                    <fmt:formatNumber value="${exhibitItem.ticketCost/100}" type="currency"
                                      currencySymbol="${currencySign}"
                                      maxFractionDigits="2"
                                      minFractionDigits="2"/>
            <td><a href="${pageContext.request.contextPath}/admin/exhibit_edit?id=${exhibitItem.id}"
                   class="btn btn-primary pt-0 pb-0"><fmt:message key="edit"/></a></td>
            <td></td>
        </tr>
    </c:forEach>
    <tr>
        <td align="center">
            <a href="${pageContext.request.contextPath}/admin/exhibit_edit" class="btn btn-primary"> <fmt:message
                    key="exhibit.add"/></a>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>