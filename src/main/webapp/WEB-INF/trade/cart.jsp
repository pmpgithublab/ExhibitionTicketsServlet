<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.exhibit"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.cart"/></h3><br>

<c:if test="${!notEnoughTickets.isEmpty()}">
    <h5 style="color: red"><fmt:message key="cart.not.enough.tickets"/></h5>
    <table class="table">
        <tr>
            <th style="width: 10%"><fmt:message key="cart.exhibitDate"/></th>
            <th style="width: 80%"><fmt:message key="cart.title.theme"/></th>
            <th style="width: 10%"><fmt:message key="cart.title.quantity"/></th>
        </tr>
        <c:forEach items="${NotEnoughTickets}" var="lackTickets">
            <tr>
                <td>${lackTickets.key.getExhibitDate()}</td>
                <td>${lackTickets.key.getExhibitTheme()}</td>
                <td>${lackTickets.key.getExhibitDescription()}</td>
                <td>${lackTickets.value}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${!expiredTickets.isEmpty()}">
    <br>
    <h5 style="color: red"><fmt:message key="cart.expired.tickets"/></h5>
    <table class="table">
        <tr>
            <th style="width: 10%"><fmt:message key="cart.exhibitDate"/></th>
            <th style="width: 30%"><fmt:message key="cart.title.theme"/></th>
            <th style="width: 6%"><fmt:message key="cart.title.sum"/></th>
            <th style="width: 6%"><fmt:message key="cart.title.quantity"/></th>
        </tr>
        <c:forEach items="${expiredTickets}" var="expiredTicket">
            <tr>
                <td>${expiredTicket.exhibitDateString}</td>
                <td>${expiredTicket.exhibitTheme}</td>
                <td>${expiredTicket.paidSum}</td>
                <td>${expiredTicket.paidQuantity}</td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
</c:if>

<table class="table">
    <thead>
    <tr>
        <th style="width: 10%"><fmt:message key="cart.exhibitDate"/></th>
        <th style="width: 30%"><fmt:message key="cart.title.theme"/></th>
        <th style="width: 30%"><fmt:message key="cart.title.hall"/></th>
        <th style="width: 6%"><fmt:message key="cart.title.sum"/></th>
        <th style="width: 6%"><fmt:message key="cart.title.quantity"/></th>
        <th style="width: 8%"><fmt:message key="cart.delete"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cart}" var="tickets">
        <form action="${pageContext.request.contextPath}/trade/ticket_delete" method="post">
            <tr>
                <fmt:parseDate value="${tickets.exhibitDate}" pattern="yyyy-MM-dd" var="exhibitDate" type="date"/>
                <c:choose>
                    <c:when test="${language != 'en'}">
                        <td><fmt:formatDate pattern="dd MMM yyyy" value="${exhibitDate}"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:formatDate pattern="MMM dd yyyy" value="${exhibitDate}"/></td>
                    </c:otherwise>
                </c:choose>
                <td>${tickets.exhibitName}</td>
                <td>${tickets.hallName}</td>
                <td><fmt:formatNumber value="${tickets.ticketSum/100}" type="currency"
                                      currencySymbol="${currencySign}" maxFractionDigits="2"
                                      minFractionDigits="2"/></td>
                <td><input name="quantity" type="number" min="1" max="99999" step="1" value="${tickets.ticketQuantity}"
                           size="5" required/></td>
                <td>
                    <input hidden name="id" value="${tickets.exhibitId}">
                    <input hidden name="date" value="${tickets.exhibitDate}">
                    <button type="submit" class="btn btn-primary pt-0 pb-0"><fmt:message key="cart.delete"/></button>
                </td>
            </tr>
        </form>
    </c:forEach>
    <tr>
        <th colspan="3"><fmt:message key="cart.total"/></th>
        <th><fmt:formatNumber value="${totalSum/100}" type="currency"
                              currencySymbol="${currencySign}" maxFractionDigits="2" minFractionDigits="2"/></th>
        <th>${totalQuantity}</th>
        <th>
            <c:if test="${cart.size() != 0}">
                <form action="${pageContext.request.contextPath}/trade/clear_cart" method="post">
                    <input hidden name="clearCart" value="yes">
                    <button type="submit" class="btn btn-primary pt-0 pb-0"><fmt:message
                            key="cart.delete.all"/></button>
                </form>
            </c:if>
        </th>
    </tr>
    </tbody>
</table>
<c:if test="${!cart.isEmpty() && notEnoughTickets.isEmpty() && expiredTickets.isEmpty()}">
    <div class="d-flex justify-content-center">
        <a href="${pageContext.request.contextPath}/trade/payment" class="btn btn-primary"><fmt:message
                key="cart.pay"/></a>
    </div>
</c:if>
</body>
</html>