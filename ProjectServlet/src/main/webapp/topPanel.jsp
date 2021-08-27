<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
>

<body class="body">


<div class="panelTop" th:fragment="panelTopUser">
    <div style="text-align: right;padding:5px;margin:5px 0px;background:#ccc;">
        <a th:href="@{/user/profile}" th:utext="#{label.profile}">Profile</a>
        <a th:href="@{/user/accounts}" th:utext="#{label.accounts}">Accounts</a>
        <a th:href="@{/user/creditCards}" th:utext="#{label.credit.cards}">Credit cards</a>
        <a th:href="@{/user/payments}" th:utext="#{label.payment}">pay</a>
        <a th:href="@{/main}" th:utext="#{label.main}">Login</a>
        <a href="javascript: document.logoutForm.submit()" role="menuitem" th:utext="#{label.logout}"> Logout</a>
        <form name="logoutForm" th:action="@{/logout}" method="post" th:hidden="true">
            <input hidden type="submit" value="Sign Out"/>
        </form>
        <span th:with="urlBuilder=${T(org.springframework.web.servlet.support.ServletUriComponentsBuilder)}">
        <a th:href="@{${urlBuilder.fromCurrentRequest().replaceQueryParam('lang', 'en-US').toUriString()}}">ENG</a>
        <a th:href="@{${urlBuilder.fromCurrentRequest().replaceQueryParam('lang', 'uk-UA').toUriString()}}">UA</a>
    </span>
    </div>
</div>

<div class="panelTop" th:fragment="panelTopGuest">
    <div style="text-align: right;padding:5px;margin:5px 0px;background:#ccc;">
        <a th:href="@{/main}" th:utext="#{label.main}">Login</a>
        <a th:href="@{/login}" th:utext="#{label.login}">Login</a>
        <a th:href="@{/registration}" th:utext="#{label.registration}">Registration</a>
        <span th:with="urlBuilder=${T(org.springframework.web.servlet.support.ServletUriComponentsBuilder)}">
        <a th:href="@{${urlBuilder.fromCurrentRequest().replaceQueryParam('lang', 'en-US').toUriString()}}">ENG</a>
        <a th:href="@{${urlBuilder.fromCurrentRequest().replaceQueryParam('lang', 'uk-UA').toUriString()}}">UA</a>
    </span>
    </div>
</div>
<div class="panelTop" th:fragment="panelTopMain">
<span sec:authorize="hasRole('ROLE_USER')">
    <div th:replace="headerTemplete :: panelTopUser">
    </div>
</span>
    <span sec:authorize="hasRole('ROLE_ADMIN')">
    <div th:replace="headerTemplete :: panelTopAdmin">
    </div>
</span>
    <span sec:authorize="!isAuthenticated()">
    <div th:replace="headerTemplete :: panelTopGuest">
    </div>
</span>
</div>
</body>
</html>