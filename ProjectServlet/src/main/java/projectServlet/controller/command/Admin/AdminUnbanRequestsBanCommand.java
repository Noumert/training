package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.service.UnbanAccountRequestService;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminUnbanRequestsBanCommand implements Command {
    private final UnbanAccountRequestService unbanAccountRequestService = new UnbanAccountRequestServiceImpl();
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));
        UnbanAccountRequest unbanAccountRequest =
                unbanAccountRequestService.findById(requestId).orElseThrow(NotFoundException::new);
        boolean resolved = true;
        unbanAccountRequestService.setResolvedByRequest(resolved,unbanAccountRequest);
        logger.info("account unban request rejected");
        return "redirect:/admin/unbanRequests";
    }
}
