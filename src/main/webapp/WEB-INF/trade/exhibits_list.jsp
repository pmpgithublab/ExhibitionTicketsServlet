<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.exhibits"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.exhibits"/></h3><br>

<div class="form-inline d-flex justify-content-center">
    <label for="exhibitThemesList" class="col-2"><fmt:message key="exhibit.title.select.theme"/></label>
    <select name="exhibitThemesList" id="exhibitThemesList" onchange="getExhibitDates()"
            class="col-4 form-control form-control">
        <option value="${selectedExhibitTheme.id}" selected>${selectedExhibitTheme.name}</option>
        <c:forEach items="${exhibitThemesList}" var="exhibitTheme">
            <c:if test="${exhibitTheme != selectedExhibitTheme}">
                <option value="${exhibitTheme.id}">${exhibitTheme.name}</option>
            </c:if>
        </c:forEach>
    </select>
</div>
<br>${isError}
<c:if test="${isError}">
    <h5 class="d-flex justify-content-center" style="color: red"><fmt:message key="exhibit.ticket.error"/></h5>
</c:if>
<c:if test="${selectedExhibitTheme != null}">
    <table class="table table-hover">
        <thead class="thead-light">
        <tr>
            <th style="width: 10%"><fmt:message key="exhibit.title.exhibitDate"/></th>
            <th style="width: 20%"><fmt:message key="exhibit.title.theme"/></th>
            <th style="width: 15%"><fmt:message key="exhibit.title.hall"/></th>
            <th style="width: 13%"><fmt:message key="exhibit.title.startDateTime"/></th>
            <th style="width: 13%"><fmt:message key="exhibit.title.endDateTime"/></th>
            <th style="width: 7%"><fmt:message key="exhibit.title.ticketCost"/></th>
            <th style="width: 5%"><fmt:message key="exhibit.title.ticketQuantity"/></th>
            <th style="width: 7%"><fmt:message key="exhibit.title.add.to.cart"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${exhibitDatesList}" var="exhibitItem">
            <form action="${pageContext.request.contextPath}/trade/ticket_add" method="post">
                <tr>
                    <fmt:parseDate value="${exhibitItem.exhibitDate}" pattern="yyyy-MM-dd" var="exhibitDate"
                                   type="date"/>
                    <fmt:parseDate value="${exhibitItem.startDateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="startDateTime"
                                   type="both"/>
                    <fmt:parseDate value="${exhibitItem.endDateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="endDateTime"
                                   type="both"/>
                    <c:choose>
                        <c:when test="${language != 'en'}">
                            <td><fmt:formatDate pattern="dd MMM yyyy" value="${exhibitDate}"/></td>
                            <td>${exhibitItem.name}</td>
                            <td>${exhibitItem.hallName}</td>
                            <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${startDateTime}"/></td>
                            <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${endDateTime}"/></td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:formatDate pattern="MMM dd yyyy" value="${exhibitDate}"/></td>
                            <td>${exhibitItem.name}</td>
                            <td>${exhibitItem.hallName}</td>
                            <td><fmt:formatDate pattern="MMM dd yyyy h:mm a" value="${startDateTime}"/></td>
                            <td><fmt:formatDate pattern="MMM dd yyyy h:mm a" value="${endDateTime}"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td><fmt:formatNumber value="${exhibitItem.ticketCost/100}" type="currency"
                                                    currencySymbol="${currencySign}"
                                                    maxFractionDigits="2"
                                                    minFractionDigits="2"/></td>
                    <td><input name="quantity" id="quantity" type="number"
                               min="1" max="100000" step="1" value="1" size="6" required/></td>
                    <td>

                        <input hidden name="id" value="${exhibitItem.id}">
                        <input hidden name="date" value="${exhibitItem.exhibitDate}">
                        <button type="submit" class="btn btn-primary pt-0 pb-0"><fmt:message
                                key="exhibit.title.add.to.cart"/></button>

                    </td>
                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<script>
    function getExhibitDates() {
        const exhibitList = document.getElementById("exhibitThemesList");
        const selectedIndex = exhibitList.options[exhibitList.selectedIndex].value;
        if (selectedIndex !== "") {
            const qm = window.location.href.indexOf('?');
            let location = window.location.href;
            if (qm !== -1) {
                location = location.slice(0, qm);
            }
            window.location.href = location + "?id=" + selectedIndex;
        }
    }
</script>
</body>
</html>