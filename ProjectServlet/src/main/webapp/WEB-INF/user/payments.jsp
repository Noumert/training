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
    <fmt:message key="label.payment"/>
</h1>

<form name="paymentForm" action="/user/payments/prepare" method="post">
    <div class="form-group">
    <p> <fmt:message key="label.account"/> </p>
        <select required name="accountId">
            <c:forEach var="account" items="${accounts}">
                <option value="${account.id}">
                        ${account.accountName} ${account.money}
                </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <p> <fmt:message key="label.money"/> </p>
        <input type="number" pattern="[0-9]+([\.,][0-9]+)?" step="0.01" name = "paymentMoney" required/>
    </div>
    <div class="form-group">
        <p> <fmt:message key="label.recipient"/> </p>
        <input type = "text" name = "recipient" required/>
    </div>
    <button type="submit"><fmt:message key="label.prepare"/></button>
</form>
</body>
</html>