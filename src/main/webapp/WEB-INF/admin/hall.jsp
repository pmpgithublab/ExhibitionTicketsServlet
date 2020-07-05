<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.hall"/></title>
</head>
<body>
<%@ include file="/WEB-INF/page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.hall"/></h3><br>

<c:if test="${isError}">
    <h5 class="d-flex justify-content-center" style="color: red"><fmt:message key="hall.error"/></h5>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/admin/hall_edit<c:if test="${hallDTO.getId() != null}">?id=${hallDTO.getId()}</c:if>">
    <table class="table table-borderless d-flex justify-content-center">
        <tr hidden>
            <td></td>
            <td><input type="text" name="id" id="id" value="${hallDTO.getId()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="hall.name"/></td>
            <td><input type="text" name="name" id="name" value="${hallDTO.getName()}" size="100" required/></td>
        </tr>
        <tr id="nameUKRow" <c:if test="${language == 'en'}"> hidden </c:if>>

            <td><fmt:message key="hall.name.uk"/></td>
            <td><input type="text" name="nameUK" id="nameUK" value="${hallDTO.getNameUK()}" size="100" required/></td>
        </tr>
    </table>
    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-primary" onclick="copyData()"><fmt:message key="save"/></button>
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