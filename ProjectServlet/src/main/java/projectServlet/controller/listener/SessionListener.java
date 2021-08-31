package projectServlet.controller.listener;

import projectServlet.model.entity.RoleType;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        String userName = (String) httpSessionEvent.getSession()
                .getAttribute("userName");
        loggedUsers.remove(userName);
        httpSessionEvent.getSession().removeAttribute("userName");
        httpSessionEvent.getSession().removeAttribute("user");
        httpSessionEvent.getSession().setAttribute("role", RoleType.ROLE_GUEST);
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
