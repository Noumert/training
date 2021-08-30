package projectServlet.controller.command.Admin;

import projectServlet.controller.command.Command;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.service.UnbanAccountRequestService;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class AdminUnbanRequestsBanCommand implements Command {
    private final UnbanAccountRequestService unbanAccountRequestService = new UnbanAccountRequestServiceImpl();
    @Override
    public String execute(HttpServletRequest request) {
        Long requestId = Long.valueOf(request.getParameter("requestId"));
        UnbanAccountRequest unbanAccountRequest =
                unbanAccountRequestService.findById(requestId).orElseThrow(NotFoundException::new);
        boolean resolved = true;
        unbanAccountRequestService.setResolvedByRequest(resolved,unbanAccountRequest);
        return "redirect:/admin/unbanRequests";
    }
}
