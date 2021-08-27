<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*, java.text.*" %>

<%!
    String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return sdf.format(new Date());
    }
%>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>
    <%@ include file="/WEB-INF/templates/header.jspf" %>
<body>
<%@ include file="/WEB-INF/templates/topPanelAdmin.jspf" %>
<br>
<span> <fmt:message key="label.greeting"/></span>
<br>
<a href="${pageContext.request.contextPath}/login"><fmt:message key="label.login"/></a>
<br>
<a href="${pageContext.request.contextPath}/registration"><fmt:message key="label.registration"/></a>
<br>
</body>
</html>
