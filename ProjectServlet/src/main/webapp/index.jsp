<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="mytaglib" uri="/WEB-INF/tlds/custom.tld"%>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
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
<%@ include file="/WEB-INF/templates/topPanel.jspf" %>
<br>
<h1> <fmt:message key="label.greeting"/></h1>
<p> <fmt:message key="label.custom.tag.lib"/></p>
<mytaglib:currentTime/>
<p> <fmt:message key="label.custom.tag.file"/></p>
<mytag:custom></mytag:custom>
</body>
</html>
