<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.payment"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.payment"/></h3><br>
<c:if test="${isSuccessful}">
    <div class="d-flex justify-content-center">
        <h5 style="color: forestgreen"><fmt:message key="payment.successful"/></h5>
    </div>
</c:if>
<c:if test="${isEmptyPaymentError}">
    <div class="d-flex justify-content-center">
        <h5 style="color: red"><fmt:message key="payment.empty.payment.error"/></h5>
    </div>
</c:if>
<c:if test="${isPaymentSystemRejectPayment}">
    <div class="d-flex justify-content-center">
        <h5 style="color: red"><fmt:message key="payment.payment.system.refuse.payment"/></h5>
    </div>
</c:if>
<c:if test="${isPaymentDataNotActualOrCannotBePaid}">
    <div class="d-flex justify-content-center">
        <h5 style="color: red"><fmt:message key="payment.payment.data.not.actual.or.cannot.be.paid"/></h5>
    </div>
</c:if>
<c:if test="${isNotEnoughTickets}">
    <div class="d-flex justify-content-center">
        <h5 style="color: red"><fmt:message key="payment.not.enough.tickets"/></h5>
    </div>
</c:if>
<table class="table d-flex justify-content-center">
    <tr align="center">
        <th colspan="2"><h5><fmt:message key="payment.information"/></h5></th>
    </tr>
    <tr>
        <td><fmt:message key="cart.totalSum"/></td>
        <td><fmt:formatNumber value="${totalSum/100}" type="currency"
                              currencySymbol="${currencySign}" maxFractionDigits="2" minFractionDigits="2"/></td>
    </tr>
    <tr>
        <td><fmt:message key="cart.totalQuantity"/></td>
        <td>${totalQuantity}</td>
    </tr>
</table>
<c:if test="${totalSum > 0 && totalQuantity > 0}">
    <div class="d-flex justify-content-center">
        <form action="${pageContext.request.contextPath}/trade/payment" method="post">
            <input hidden name="totalQuantity" value="${totalQuantity}">
            <input hidden name="totalSum" value="${totalSum}">
            <input hidden name="currencySign" value="${currencySign}">
            <button type="submit" class="btn btn-primary"><fmt:message key="payment.pay"/></button>
        </form>
    </div>
</c:if>

</body>
</html>