package projectServlet.controller.command.Admin;

import projectServlet.controller.command.Command;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.UnbanAccountRequestDtoConverterImpl;
import projectServlet.model.dto.UnbanAccountRequestDTO;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.service.UnbanAccountRequestProcessingService;
import projectServlet.model.service.UnbanAccountRequestProcessingServiceImpl;
import projectServlet.model.service.UnbanAccountRequestService;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class AdminUnbanRequestsCommand implements Command {
    private final UnbanAccountRequestService unbanAccountRequestService
            = new UnbanAccountRequestServiceImpl();
    private final EntityDtoConverter<UnbanAccountRequest, UnbanAccountRequestDTO> unbanAccountRequestDtoConverter=
            new UnbanAccountRequestDtoConverterImpl();
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("requests", unbanAccountRequestDtoConverter
                .convertEntityListToDtoList(unbanAccountRequestService.findByResolved(false)));
        return "/WEB-INF/admin/unbanRequests.jsp";
    }
}
