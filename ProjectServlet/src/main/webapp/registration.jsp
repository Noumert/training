<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login in system</title>

</head>
<body>

        <h1>Вход в систему</h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/registration">
         <p>FirstName</p>
         <input type="text" name="firstName" required><br>
         <p>LastName</p>
         <input type="text" name="lastName" required><br>
         <p>Email</p>
         <td><input type="text" name="email" required><br>
         <p>Password</p>
         <input type="password" name="password" required><br>
         <p style="color:red"><%=(request.getParameter("error") == null) ? ""
                                 : "Duplicated email"%></p>
         <input class="button" type="submit" value="Войти">
        </form>
        <br/>

</body>
</html>