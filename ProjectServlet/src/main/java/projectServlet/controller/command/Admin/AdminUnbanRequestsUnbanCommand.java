package projectServlet.controller.command.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectServlet.controller.command.Command;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.service.UnbanAccountRequestProcessingService;
import projectServlet.model.service.UnbanAccountRequestProcessingServiceImpl;
import projectServlet.model.service.UnbanAccountRequestService;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminUnbanRequestsUnbanCommand implements Command {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final UnbanAccountRequestProcessingService unbanAccountRequestProcessingService
            = new UnbanAccountRequestProcessingServiceImpl();
    private final UnbanAccountRequestService unbanAccountRequestService = new UnbanAccountRequestServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));
        UnbanAccountRequest unbanAccountRequest =
                unbanAccountRequestService.findById(requestId).orElseThrow(NotFoundException::new);
        final boolean resolved = true;
        final boolean ban = false;
        unbanAccountRequestProcessingService.unbanAndSetResolvedByRequest(ban,resolved,unbanAccountRequest);
        logger.info("account unbanned successfully");
        return "redirect:/admin/unbanRequests";
    }
}
