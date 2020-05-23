<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.user.statistic"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.user.statistic"/></h3><br>

<table class="table">
    <thead>
    <tr>
        <th style="width: 10%"><fmt:message key="user.statistic.purchase.date"/></th>
        <th style="width: 70%"><fmt:message key="user.statistic.exhibit.theme"/></th>
        <th style="width: 10%"><fmt:message key="user.statistic.ticket.sum"/></th>
        <th style="width: 10%"><fmt:message key="user.statistic.ticket.quantity"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ReportDTOS.getItems()}" var="purchase">
        <fmt:parseDate value="${purchase.exhibitDate}" pattern="yyyy-MM-dd" var="exhibitDate" type="date"/>
        <tr>
            <c:choose>
                <c:when test="${language != 'en'}">
                    <td><fmt:formatDate pattern="dd MMM yyyy" value="${exhibitDate}"/></td>
                </c:when>
                <c:otherwise>
                    <td><fmt:formatDate pattern="MMM dd yyyy" value="${exhibitDate}"/></td>
                </c:otherwise>
            </c:choose>
            <td>${purchase.exhibitName}</td>
            <td class="text-right pr-4"><fmt:formatNumber value="${purchase.paidSum/100}" type="currency"
                                                          currencySymbol="${currencySign}"
                                                          maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
            <td class="text-right pr-4">${purchase.ticketQuantity}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="../page_parts/pagination.jsp" %>
</body>
</html>