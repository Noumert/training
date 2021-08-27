<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login in system</title>

</head>
<body>

        <h1>Вход в систему</h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/login">

            <p>email</p>
            <input type="text" name="name" required><br/>
            <p>password</p>
            <input type="password" name="pass" required><br/><br/>
            <p style="color:red"><%=(request.getParameter("error") == null) ? ""
                                 : "username/password incorrect"%></p>
            <input class="button" type="submit" value="Войти">
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/registration">Registration</a>

</body>
</html>