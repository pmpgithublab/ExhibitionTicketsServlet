<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.halls"/></title>
</head>
<body>
<%@ include file="/WEB-INF/page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.halls"/></h3><br>

<table class="table">
    <thead>
    <tr>
        <th width="10%"></th>
        <th><fmt:message key="halls.title.name"/></th>
        <th width="10%"><fmt:message key="hall.edit"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${hallsList}" var="hall">
        <tr>
            <td></td>
            <td>${hall.name}</td>
            <td><a href="${pageContext.request.contextPath}/admin/hall_edit?id=${hall.id}" class="btn btn-primary pt-0 pb-0"><fmt:message key="hall.edit"/></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class=" d-flex justify-content-center">
    <a href="${pageContext.request.contextPath}/admin/hall_edit" class="btn btn-primary"><fmt:message
            key="hall.add"/></a>
</div>


</body>
</html>