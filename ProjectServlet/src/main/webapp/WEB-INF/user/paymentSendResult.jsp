<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>

<c:if test="${param.error=='true'}">
    <div class="alert alert-danger">
        <fmt:message key="label.error"/>
    </div>
</c:if>
<c:if test="${param.noMoneyError=='true'}">
    <div class="alert alert-danger">
        <fmt:message key="label.no.money.error"/>
    </div>
</c:if>
<c:if test="${param.banError=='true'}">
    <div class="alert alert-danger">
        <fmt:message key="label.ban.error"/>
    </div>
</c:if>


<a href="/user/profile?payPage=${payPage}&paySortBy=${paySortBy}&payAsc=${payAsc}&accPage=${accPage}&accSortBy=${accSortBy}&accAsc=${accAsc}">
    <fmt:message key="label.back.to.profile.page"/>
</a>
</body>
</html>