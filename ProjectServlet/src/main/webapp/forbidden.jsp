<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>

<h1 style="color:red"><fmt:message key="label.forbidden"/></h1>
<a href="${pageContext.request.contextPath}/main"><fmt:message key="label.main"/></a>

</body>
</html>
