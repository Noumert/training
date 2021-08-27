<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>
<head>

    <%@ include file="/WEB-INF/templates/header.jspf" %>

</head>
<body>

<%@ include file="/WEB-INF/templates/topPanelAdmin.jspf" %>

<h1><fmt:message key="label.registration"/></h1><br/>
<form method="post" action="${pageContext.request.contextPath}/registration">
    <p><fmt:message key="label.first.name"/></p>
    <input type="text" name="firstName" required><br>
    <p><fmt:message key="label.last.name"/></p>
    <input type="text" name="lastName" required><br>
    <p><fmt:message key="label.email"/></p>
    <td><input type="text" name="email" required><br>
        <p><fmt:message key="label.password"/></p>
        <input type="password" name="password" required><br>
        <c:if test="${param.error=='true'}">
        <p style="color:red"><fmt:message key="label.registration.error"/></p>
        </c:if>
        <br>
        <button type="submit" class="btn btn-success"><fmt:message key="label.form.submit"/></button>
</form>
<br/>

</body>
</html>