<!DOCTYPE html>
<html>
<head>
    <%@ include file="../page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.exhibit"/></title>
</head>
<body>
<%@ include file="../page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.exhibit"/></h3><br>

<c:if test="${isError}">
    <h5 class="text-center" style="color: red"><fmt:message key="exhibit.error"/></h5>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/admin/exhibit_edit<c:if test="${exhibitDTO.getId() != null}">?id=${exhibitDTO.getId()}</c:if>">
    <table class="table d-flex justify-content-center">
        <tr hidden>
            <td><fmt:message key="exhibit.id"/></td>
            <td><input type="text" name="id" value="${exhibitDTO.getId()}"/></td>
        </tr>
        <tr>
            <td width="200"><fmt:message key="exhibit.theme"/></td>
            <td><input type="text" name="name" value="${exhibitDTO.getName()}" id="name" size="100" required/></td>
        </tr>

        <c:choose>
        <c:when test="${language != 'en'}">
        <tr id="nameUKRow">
            </c:when>
            <c:otherwise>
        <tr hidden id="nameUKRow">
            </c:otherwise>
            </c:choose>
            <td width="200"><fmt:message key="exhibit.theme.uk"/></td>
            <td><input type="text" name="nameUK" value="${exhibitDTO.getNameUK()}" id="nameUK" size="100" required/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="exhibit.startDateTime"/></td>
            <td><input type="datetime-local" name="startDateTime" value="${exhibitDTO.getStartDateTime()}" size="30"
                       required/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="exhibit.endDateTime"/></td>
            <td><input type="datetime-local" name="endDateTime" dataformatas="" value="${exhibitDTO.getEndDateTime()}"
                       size="30" required/></td>
        </tr>
        <tr>
            <td><fmt:message key="exhibit.maxVisitorsPerDay"/></td>
            <td><input type="number" name="maxVisitorsPerDay" value="${exhibitDTO.getMaxVisitorsPerDay()}"
                       size="20" min="1" step="1" max="99999" required/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="exhibit.ticketCost"/></td>
            <td>
                <input type="text" name="ticketCost" id="ticketCost" class="ticketCost" placeholder="0.00"
                       value="${exhibitDTO.getTicketCost()/100}"
                       size="10" pattern="^\d{1,7}\.\d{2}$" title="####.##" required/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="exhibit.hall"/></td>
            <td>
                <select name="hallId">
                    <option value="${selectedHall.getId()}" selected>${selectedHall.getName()}</option>
                    <c:forEach items="${halls}" var="hall">
                        <c:if test="${hall != selectedHall}">
                            <option value="${hall.getId()}">${hall.getName()}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-primary" onclick="copyData()"><fmt:message key="exhibit.save"/></button>
    </div>
</form>

<script type="text/javascript">
    function copyData() {
        if (document.getElementById("nameUKRow").hidden) {
            document.getElementById('nameUK').value = document.getElementById('name').value;
        }
    }
</script>

</body>
</html>