package projectServlet.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("lang") != null) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}
}