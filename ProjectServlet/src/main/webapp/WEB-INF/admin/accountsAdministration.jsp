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
    <fmt:message key="label.accounts"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <thead>
    <tr>
        <th><fmt:message key="label.email"/></th>
        <th><fmt:message key="label.first.name"/></th>
        <th><fmt:message key="label.last.name"/></th>
        <th><fmt:message key="label.account.number"/></th>
        <th><fmt:message key="label.account.name"/></th>
        <th><fmt:message key="label.money"/></th>
        <th><fmt:message key="label.banned"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty userAccounts}">
        <tr>
            <td colspan="4"><fmt:message key="label.no.accounts"/></td>
        </tr>
    </c:if>
    <c:forEach var="userAccount" items="${userAccounts}">
        <tr>
            <td><span> ${userAccount.email} </span></td>
            <td><span> ${userAccount.firstName} </span></td>
            <td><span> ${userAccount.lastName} </span></td>
            <td><span> ${userAccount.accountNumber} </span></td>
            <td><span> ${userAccount.accountName} </span></td>
            <td><span> ${userAccount.money} </span></td>
            <td><span>
                <c:choose>
                    <c:when test="${userAccount.ban}">
                        <fmt:message key="label.unbanned.account"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.banned.account"/>
                    </c:otherwise>
                </c:choose>
            </span></td>
            <c:if test="${!userAccount.ban}">
            <td>
                <form name="banForm" action="admin/accounts/ban" method="post">
                    <input type="hidden" name="accountId" value="${userAccount.id}"/>
                    <button type="submit"><fmt:message key="label.ban"/></button>
                </form>
            </td>
            </c:if>
            <c:if test="${userAccount.ban}">
            <td>
                <form name="unbanForm" action="/admin/accounts/unban" method="post">
                    <input type="hidden" name="accountId" value="${userAccount.id}"/>
                    <button type="submit"><fmt:message key="label.ban"/></button>
                </form>
            </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>