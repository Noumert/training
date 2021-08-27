package projectServlet.controller.command;



import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                            RoleType role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
        session.setAttribute("role", role);
    }

    static void clearSessionAndContext(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        loggedUsers.remove((String)context.getAttribute("userName"));
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        context.setAttribute("user",new User());
        context.setAttribute("userName","Guest");
        session.setAttribute("role",RoleType.ROLE_GUEST);
    }

    static void setUser(HttpServletRequest request,
                            User user) {
        ServletContext context = request.getServletContext();
        context.setAttribute("user", user);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }
}
