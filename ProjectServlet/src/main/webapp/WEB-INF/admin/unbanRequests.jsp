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
        <th> <fmt:message key="label.date.time"/> </th>
        <th> <fmt:message key="label.account.name"/> </th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty requests}">
    <tr>
        <td colspan="4"> <fmt:message key="label.no.requests"/> </td>
    </tr>
    </c:if>
    <c:forEach var="req" items="${requests}">
    <tr>
        <td><span> ${req.dateTime} </span></td>
        <td><span> ${req.account.accountName} </span></td>
        <td>
            <form name="banForm" action="/admin/unbanRequests/refuse" method="post">
                <input type = "hidden" name = "requestId" value = "${req.id}" />
                <button type="submit"><fmt:message key="label.refuse"/></button>
            </form>
        </td>
        <td>
            <form name="unbanForm" action="/admin/unbanRequests/unban" method="post">
                <input type = "hidden" name = "requestId" value = "${req.id}" />
                <button type="submit"><fmt:message key="label.unban"/></button>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>