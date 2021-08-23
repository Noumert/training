<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login in system</title>

</head>
<body>

        <h1>Вход в систему</h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/login">

            <input type="text" name="name"><br/>
            <input type="password" name="pass"><br/><br/>
            <input class="button" type="submit" value="Войти">

        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/logout">На головну</a>

</body>
</html>