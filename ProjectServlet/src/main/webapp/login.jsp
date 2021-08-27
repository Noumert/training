<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/templates/bundle.jspf" %>

<html>

<%@ include file="/WEB-INF/templates/header.jspf" %>

<body>

<%@ include file="/WEB-INF/templates/topPanelAdmin.jspf" %>

<h1><fmt:message key="label.login"/></h1><br/>
<form method="post" action="${pageContext.request.contextPath}/login">

    <p><fmt:message key="label.email"/></p>
    <input type="text" name="name" required><br/>
    <p><fmt:message key="label.password"/></p>
    <input type="password" name="pass" required><br/><br/>
    <p style="color:red"><%=(request.getParameter("error") == null) ? ""
            : "username/password incorrect"%>
    </p>
    <c:if test="${param.error=='true'}">
        <p style="color:red"><fmt:message key="label.login.error"/></p>
    </c:if>
    <button type="submit" class="btn btn-success"><fmt:message key="label.form.submit"/></button>
</form>
<br/>
<a href="${pageContext.request.contextPath}/registration">Registration</a>

</body>
</html>