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
        System.out.println(session);
        System.out.println(session.getAttribute("role"));
        System.out.println(context.getAttribute("loggedUsers"));
        if(session.getAttribute("role")==null){
            session.setAttribute("role",RoleType.ROLE_GUEST);
        }
        if (session.getAttribute("role").equals(RoleType.ROLE_ADMIN.name())){
//            res.sendRedirect("/admin");
            System.out.println("ADMIN");
        } else if(session.getAttribute("role").equals(RoleType.ROLE_USER.name())) {
//            res.sendRedirect("/user");
            System.out.println("USER");
        } else {
//            res.sendRedirect("/login");
            System.out.println("GUEST");
        }
//        res.se


        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
