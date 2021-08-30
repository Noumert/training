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
        <th> <fmt:message key="label.data.time"/> </th>
        <th> <fmt:message key="label.account.name"/> </th>
    </tr>
    </thead>
    <tbody>
    <tr th:if="${requests.empty}">
        <td colspan="4" th:utext="#{label.no.accounts}"> no accounts </td>
    </tr>
    <tr th:each="request : ${requests}">
        <td><span th:text="${request.dateTime}"> data and time </span></td>
        <td><span th:text="${request.account.accountName}"> Account name </span></td>
        <td>
            <form name="banForm" th:action="@{/admin/unbanRequests/refuse}" method="post">
                <input type = "hidden" name = "requestId" th:value = "${request.id}" />
                <button type="submit" th:text="#{label.refuse}"></button>
            </form>
        </td>
        <td>
            <form name="unbanForm" th:action="@{/admin/unbanRequests/unban}" method="post">
                <input type = "hidden" name = "requestId" th:value = "${request.id}" />
                <button type="submit" th:text="#{label.unban}"></button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>