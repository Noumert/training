package projectServlet.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.Servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Locale;

public class SessionLocaleFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String lang = req.getParameter("lang");
        if (lang != null) {
            req.getSession().setAttribute("lang", lang);
            Locale.setDefault(Locale.forLanguageTag(lang));
            logger.info("Language changed to {}",lang);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}
}