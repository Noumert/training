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
<c:if test="${param.success=='true'}">
    <div class="alert alert-info">
        <fmt:message key="label.operation.success"/>
    </div>
</c:if>

<a href="/user/payments"><fmt:message key="label.back.to.payment.page"/></a>
</body>
</html>