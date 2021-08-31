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
    <fmt:message key="label.credit.cards"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <thead>
    <tr>
        <th><fmt:message key="label.credit.card"/></th>
        <th><fmt:message key="label.expiration.date"/></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty userCards}">
        <tr>
            <td colspan="4"><fmt:message key="label.no.cards"/></td>
        </tr>
    </c:if>
    <c:forEach var="userCard" items="${userCards}">
        <tr>
            <td><span> ${userCard.cardNumber} </span></td>
            <td><span> ${userCard.expirationDate} </span></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form name="addForm" action="/user/creditCards/add" method="post">
    <select required name="accountId">
        <c:forEach var="account" items="${accounts}">
        <option value="${account.id}">
                ${account.accountName} ${account.money}
        </option>
        </c:forEach>
    </select>
    <button type="submit"><fmt:message key="label.add"/></button>
</form>
</body>
</html>