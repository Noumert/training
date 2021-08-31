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
        <th><fmt:message key="label.account.number"/></th>
        <th><fmt:message key="label.account.name"/></th>
        <th><fmt:message key="label.money"/></th>
        <th><fmt:message key="label.banned"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty accounts}">
        <tr>
            <td colspan="4"><fmt:message key="label.no.accounts"/></td>
        </tr>
    </c:if>
    <c:forEach var="account" items="${accounts}">
        <tr>
            <td><span> ${account.accountNumber} </span></td>
            <td><span> ${account.accountName} </span></td>
            <td><span> ${account.money} </span></td>
            <td><span>
                <c:choose>
                    <c:when test="${account.ban}">
                        <fmt:message key="label.banned.account"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.unbanned.account"/>
                    </c:otherwise>
                </c:choose>
            </span></td>
            <c:if test="${!account.ban}">
            <td>
                <form name="banForm" action="/user/accounts/ban" method="post">
                    <input type="hidden" name="accountId" value="${account.id}"/>
                    <button type="submit"><fmt:message key="label.ban"/></button>
                </form>
            </td>
            </c:if>
            <c:if test="${account.ban}">
            <td>
                <form name="unbanForm" action="/user/accounts/unban" method="post">
                    <input type="hidden" name="accountId" value="${account.id}"/>
                    <button type="submit"><fmt:message key="label.unban"/></button>
                </form>
            </td>
            </c:if>
            <td>
                <form name="topUpForm" action="/user/accounts/topUpForm" method="get">
                    <input type = "hidden" name = "accountId"  value = "${account.id}" />
                    <button type="submit" <c:if test="${account.ban}"><c:out value="disabled='disabled'"/></c:if>>
                        <fmt:message key="label.top.up"/>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form name="addForm" action="/user/accounts/add" method="post">
    <button type="submit"><fmt:message key="label.add"/></button>
</form>
</body>
</html>