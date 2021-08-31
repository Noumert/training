<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.*" %>


<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>
<c:choose>
    <c:when test="${userLoggedError}">
        <h1 style="color:red"><fmt:message key="label.already.logged"/></h1>
    </c:when>
    <c:otherwise>
        <h1 style="color:red">500 <fmt:message key="label.error"/></h1>
    </c:otherwise>
</c:choose>
<a href="${pageContext.request.contextPath}/main"><fmt:message key="label.main"/></a>
<br>
</body>
</html>