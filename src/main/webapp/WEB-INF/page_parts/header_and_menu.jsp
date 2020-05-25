<header class="header">
<%--    table-borderless--%>
    <table class="table table-sm m-0 table-borderless">
        <tr>
            <td rowspan="2" width="7%"></td>
            <td rowspan="2"><h1 style="color: darkblue"><i><fmt:message key="company.name"/></i></h1></td>
            <td rowspan="2" width="15%">
                <c:if test="${user != null}">
                    <c:choose>
                        <c:when test="${language != 'en'}">
                            <fmt:message key="user.hello"/>&nbsp;${userNameUK}
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="user.hello"/>&nbsp;${userName}
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
            <td width="7%">
                <a href="javascript: changeLang('en');"><fmt:message key="language.en"/></a>&nbsp;&nbsp;&nbsp;
                <a href="javascript: changeLang('uk');"><fmt:message key="language.uk"/></a>
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${sessionScope.userRole != 'ROLE_ANONYMOUS'}">
                    <a href="${pageContext.request.contextPath}/logout"><fmt:message key="logout"/></a>
                </c:if>
            </td>
        </tr>
    </table>

    <table class="table table-sm table-borderless m-0">
        <tr class="d-flex justify-content-center">
            <td class="m-0 p-0" colspan="3">
                <c:if test="${userRole == 'ROLE_ANONYMOUS'}">
                    <a href="${pageContext.request.contextPath}"><fmt:message key="menu.home"/></a> |
                    <a href="${pageContext.request.contextPath}/registration">
                        <fmt:message key="menu.registration"/></a> |
                    <a href="${pageContext.request.contextPath}/login"><fmt:message key="menu.login"/></a>
                </c:if>

                <c:if test="${sessionScope.userRole == 'ROLE_ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin/halls_list"><fmt:message key="menu.halls"/></a> |
                    <a href="${pageContext.request.contextPath}/admin/hall_edit"><fmt:message key="menu.add.hall"/></a> |
                    <a href="${pageContext.request.contextPath}/admin/exhibits_list">
                        <fmt:message key="menu.exhibits"/></a> |
                    <a href="${pageContext.request.contextPath}/admin/exhibit_edit">
                        <fmt:message key="menu.add.exhibit"/></a> |
                    <a href="${pageContext.request.contextPath}/admin/report?page=0">
                        <fmt:message key="menu.admin.statistic"/></a>
                </c:if>

                <c:if test="${sessionScope.userRole == 'ROLE_USER'}">
                    <a href="${pageContext.request.contextPath}/trade/exhibits_list">
                        <fmt:message key="menu.current.exhibits"/></a> |
                    <a href="${pageContext.request.contextPath}/trade/cart"><fmt:message key="menu.cart"/></a> |
                    <a href="${pageContext.request.contextPath}/trade/report?page=0">
                        <fmt:message key="menu.user.statistic"/></a>
                </c:if>
            </td>
        </tr>
    </table>
    <br>

</header>

<script>
    function changeLang(lang) {
        if ((window.location.href.indexOf('?')) == -1) {
            window.location.href = (window.location.href + '?lang=' + lang);
        } else {
            let location = window.location.href;
            location = location.replace('?lang=en', '?');
            location = location.replace('?lang=uk', '?');
            location = location.replace('?', '?lang=' + lang + '&');
            location = location.replace('&&', '&');
            if (location.endsWith('&')) {
                location = location.slice(0, -1);
            }
            window.location.href = location;
        }
    }
</script>
