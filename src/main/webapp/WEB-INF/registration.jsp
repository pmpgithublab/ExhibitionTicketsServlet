<!DOCTYPE html>
<html>
<head>
    <%@ include file="page_parts/jstl_and_bootstrap.jsp" %>
    <title><fmt:message key="page.title.registration"/></title>
</head>
<body>
<%@ include file="page_parts/header_and_menu.jsp" %>

<h3 class="d-flex justify-content-center"><fmt:message key="page.title.registration"/></h3><br>

<form action="${pageContext.request.contextPath}/registration" method="post" name="exhibitForm">
    <c:if test="${!isFieldsFilled}">
        <label class="d-flex justify-content-center" style="color: red"><fmt:message
                key="registration.user.fill.fields"/></label>
    </c:if>
    <c:if test="${isUserEmailError}">
        <label class="d-flex justify-content-center" style="color: red"><fmt:message
                key="registration.email.exists"/></label>
    </c:if>

    <table class="table d-flex justify-content-center">
        <tbody>
        <tr>
            <td><label for="name"><fmt:message key="registration.user.name"/></label></td>
            <td><input type="text" name="name" id="name" pattern="${name_pattern_regex}" value="${userName}" required/></td>
        <tr  <c:if test="${language == 'en'}"> hidden </c:if> id="nameUKRow">
            <td><label for="nameUK"><fmt:message key="registration.user.name.uk"/></label></td>
            <td><input type="text" name="nameUK" id="nameUK" pattern="${name_uk_pattern_regex}" value="${userNameUK}"
                       required/>
            </td>
        </tr>
        <tr>
            <td><label for="email"><fmt:message key="email"/></label></td>
            <td><input type="email" name="email" id="email" pattern="${email_pattern_regex}" value="${userEmail}" required/>
            </td>
        </tr>
        <tr>
            <td><label for="password"><fmt:message key="password"/></label></td>
            <td><input type="password" name="password" id="password" pattern="${password_pattern_regex}" required/></td>
        </tr>
        </tbody>
    </table>
    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-primary" onclick="copyData()"><fmt:message
                key="registration.register"/></button>
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