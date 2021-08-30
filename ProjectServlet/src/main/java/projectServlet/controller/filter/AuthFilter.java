package projectServlet.controller.filter;

import projectServlet.model.entity.RoleType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();
        RoleType role = (RoleType) session.getAttribute("role");
        if (role == null) {
            session.setAttribute("role", RoleType.ROLE_GUEST);
            role = (RoleType) session.getAttribute("role");
        }
        System.out.println(session);
        System.out.println(role);
        System.out.println(context.getAttribute("loggedUsers"));

        if (!isPermitted(role, req.getRequestURI())) {
            req.getRequestDispatcher("/forbidden.jsp").forward(req, res);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermitted(RoleType role, String path) {
        boolean permitted = false;
        switch (role) {
            case ROLE_ADMIN:
                permitted = path.matches("(/admin.*)|(/logout)");
                break;
            case ROLE_USER:
                permitted = path.matches("(/user.*)|(/logout)");
                break;
            case ROLE_GUEST:
                permitted = path.matches("(/login)|(/registration)|(/logout)");
                break;
        }
        return permitted | path.matches("(/)|(/main)");
    }

    @Override
    public void destroy() {

    }
}
