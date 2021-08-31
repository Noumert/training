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
    <fmt:message key="label.profile"/>
</h1>

<h3>
    <span> <fmt:message key="label.email"/> </span>:
    <span> ${user.email} </span>
</h3>
<h3>
    <span> <fmt:message key="label.first.name"/> </span>:
    <span> ${user.firstName} </span>
</h3>
<h3>
    <span> <fmt:message key="label.last.name"/> </span>:
    <span> ${user.lastName} </span>
</h3>

<h1>
    <fmt:message key="label.accounts"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <thead>
    <tr>
        <th>
            <a href="/user/profile?accPage=${accPage}&accSortBy=account_number&accAsc=${!accAsc}&payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}">
                <fmt:message key="label.account.number"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?accPage=${accPage}&accSortBy=account_name&accAsc=${!accAsc}&payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}">
                <fmt:message key="label.account.name"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?accPage=${accPage}&accSortBy=money&accAsc=${!accAsc}&payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}">
                <fmt:message key="label.money"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?accPage=${accPage}&accSortBy=ban&accAsc=${!accAsc}&payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}">
                <fmt:message key="label.ban"/>
            </a>
        </th>
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
        <td>
            <span>
                <c:choose>
                    <c:when test="${account.ban}">
                        <fmt:message key="label.banned.account"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.unbanned.account"/>
                    </c:otherwise>
                </c:choose>
            </span>
        </td>
    </tr>
    </c:forEach>
</table>

<%--<div th:replace="pagination :: acc">--%>
<%--</div>--%>

<h1>
    <fmt:message key="label.payments"/>
</h1>

<table style="border-spacing: 7px 11px; border-collapse: separate;">
    <thead>
    <tr>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=account_name&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.account.name"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=recipient&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.recipient"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=payment_money&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.money"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=payment_number&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.payment.number"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=date_time&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.date.time"/>
            </a>
        </th>
        <th>
            <a href="/user/profile?payPage=${payPage}&paySortBy=status&payAsc=${!payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
                <fmt:message key="label.status"/>
            </a>
        </th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty payments}">
    <tr>
        <td colspan="4"> <fmt:message key="label.no.payments"/></td>
    </tr>
    </c:if>
    <c:forEach var="payment" items="${payments}">
    <tr>
        <td><span> ${payment.account.accountName} </span></td>
        <td><span> ${payment.recipient} </span></td>
        <td><span> ${payment.money} </span></td>
        <td><span> ${payment.paymentNumber} </span></td>
        <td><span> ${payment.dateTime} </span></td>
        <td>
            <span>
                <c:choose>
                    <c:when test="${payment.status == 'PREPARED'}">
                        <fmt:message key="label.payment.prepared"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.payment.sent"/>
                    </c:otherwise>
                </c:choose>
            </span>
        </td>
        <c:if test="${payment.status== 'PREPARED'}">
        <td>
            <form name="sendForm" action="/user/profile/send?payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}" method="post">
                <input type="hidden" name="paymentId" value="${payment.id}"/>
                <button type="submit"><fmt:message key="label.send"/></button>
            </form>
        </td>
        </c:if>
    </tr>
    </c:forEach>
</table>

<%--<div th:replace="pagination :: pay">--%>
<%--</div>--%>

</body>
</html>