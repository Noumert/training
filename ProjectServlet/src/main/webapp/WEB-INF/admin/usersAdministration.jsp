<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>

<%@ include file="/WEB-INF/templates/topPanel.jspf" %>

<c:if test="${param.error=='true'}">
    <div class="alert alert-info">
        <fmt:message key="label.error"/>
    </div>
</c:if>

<h1>
    <fmt:message key="label.users"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <thead>
    <tr>
        <th><fmt:message key="label.email"/></th>
        <th><fmt:message key="label.first.name"/></th>
        <th><fmt:message key="label.last.name"/></th>
        <th><fmt:message key="label.banned"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty users}">
        <tr>
            <td colspan="4"><fmt:message key="label.no.accounts"/></td>
        </tr>
    </c:if>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><span> ${user.email}</span></td>
            <td><span> ${user.firstName}</span></td>
            <td><span> ${user.lastName}</span></td>
            <td><span><c:choose>
                <c:when test="${user.accountNonLocked}">
                    <fmt:message key="label.unbanned.account"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="label.banned.account"/>
                </c:otherwise>
            </c:choose>
            </span>
            </td>
            <c:if test="${user.accountNonLocked}">
                <td>
                    <form name="banForm" action="/admin/users/ban" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit"><fmt:message key="label.ban"/></button>
                    </form>
                </td>
            </c:if>
            <c:if test="${!user.accountNonLocked}">
                <td>
                    <form name="unbanForm" action="/admin/users/unban" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit"><fmt:message key="label.unban"/></button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>