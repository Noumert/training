package projectServlet.controller;

import projectServlet.controller.command.*;
import projectServlet.controller.command.Admin.*;
import projectServlet.controller.command.User.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {

        servletConfig.getServletContext()
                .setAttribute("loggedUsers",
                        new HashSet<String>());
        commands.put("/admin/unbanRequests/refuse",
                new AdminUnbanRequestsBanCommand());
        commands.put("/admin/unbanRequests/unban",
                new AdminUnbanRequestsUnbanCommand());
        commands.put("/admin/unbanRequests",
                new AdminUnbanRequestsCommand());
        commands.put("/admin/accounts/ban",
                new AdminAccountBanCommand());
        commands.put("/admin/accounts/unban",
                new AdminAccountUnbanCommand());
        commands.put("/admin/accounts",
                new AccountAdministratingCommand());
        commands.put("/admin/users/ban",
                new AdminUserBanCommand());
        commands.put("/admin/users/unban",
                new AdminUserUnbanCommand());
        commands.put("/admin/users",
                new UserAdministratingCommand());

        commands.put("/user/profile",
                new UserProfileCommand());
        commands.put("/user/profile/send",
                new UserSendPaymentCommand());
        commands.put("/user/profile/sendResult",
                new UserSendPaymentResultCommand());
        commands.put("/user/payments/prepare",
                new UserPaymentsPrepareCommand());
        commands.put("/user/payments/prepareResult",
                new UserPaymentsPrepareResultCommand());
        commands.put("/user/payments",
                new UserPaymentsCommand());
        commands.put("/user/creditCards",
                new UserCreditCardsCommand());
        commands.put("/user/creditCards/add",
                new UserCreditCardAddCommand());
        commands.put("/user/accounts/unbanResult",
                new UserAccountUnbanResultCommand());
        commands.put("/user/accounts/ban",
                new UserAccountBanCommand());
        commands.put("/user/accounts/unban",
                new UserAccountUnbanCommand());
        commands.put("/user/accounts/topUpForm/topUp",
                new UserAccountTopUp());
        commands.put("/user/accounts/topUpForm",
                new UserAccountTopUpForm());
        commands.put("/user/accounts/add",
                new UserAccountAddCommand());
        commands.put("/user/accounts",
                new UserAccountsCommand());
        commands.put("/main",
                new MainCommand());
        commands.put("/user",
                new UserCommand());
        commands.put("/admin",
                new AdminCommand());
        commands.put("/registration",
                new RegistrationCommand());
        commands.put("/logout",
                new LogOutCommand());
        commands.put("/login",
                new LoginCommand());
        commands.put("/exception",
                new ExceptionCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Command command = commands.getOrDefault(path,
                (r) -> "/index.jsp");
        System.out.println(command.getClass().getName());
        String page = command.execute(request);
        //request.getRequestDispatcher(page).forward(request,response);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
