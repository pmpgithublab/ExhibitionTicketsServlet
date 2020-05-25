<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.login"/></title>
</head>
<body>
<%@ include file="page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.login"/></h3><br>

<form action="${pageContext.request.contextPath}/login" method="post">
    <c:if test="${isError}">
        <p class="d-flex justify-content-center" style="color: #F44336"><fmt:message key="login.error"/></p>
    </c:if>
    <table class="table d-flex justify-content-center">
        <tbody>
        <tr>
            <td><label for="email"><fmt:message key="email"/></label></td>
            <td><input type="email" name="email" id="email" size="30" required/></td>
        </tr>
        <tr>
            <td><label for="password"><fmt:message key="password"/></label></td>
            <td><input type="password" name="password" id="password" size="30" required/></td>
        </tr>
        </tbody>
    </table>
    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-primary" value=""><fmt:message key="login.login"/></button>
    </div>
</form>
</body>
</html>