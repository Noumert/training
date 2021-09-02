<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>

<%@ include file="/WEB-INF/templates/topPanel.jspf" %>

<c:if test="${param.error=='true'}">
    <div class="alert alert-danger">
        <fmt:message key="label.error"/>
    </div>
</c:if>


<h1>
    <fmt:message key="label.accounts.topUp"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <tbody>
    <tr>
        <th> <fmt:message key="label.account.number"/> </th><td><span> ${account.accountNumber} </span></td>
    </tr>
    <tr>
        <th> <fmt:message key="label.account.name"/> </th><td><span> ${account.accountName} </span></td>
    </tr>
    <tr>
        <th> <fmt:message key="label.money"/> </th><td><span> ${account.money} </span></td>
    </tr>
    <tr>
        <td>
            <form name="topUpForm" action="/user/accounts/topUpForm/topUp" method="post">
                <input id="topUpMoney" type = "number" pattern="[0-9]+([\.,][0-9]+)?"
                       step="0.01" name = "topUpMoney" required/>
                <c:if test="${moneyIncorrect}">
                    <p style="color:red"><fmt:message key="label.money.not.valid"/></p>
                </c:if>
                <input type = "hidden" name = "accountId" value = "${account.id}"  />
                <button type="submit" <c:if test="${account.ban}"><c:out value="disabled='disabled'"/></c:if>>
                    <fmt:message key="label.top.up"/>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>