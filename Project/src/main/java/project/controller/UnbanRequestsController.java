package project.controller;


import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.UnbanAccountRequest;
import project.model.EntityDtoConverter;
import project.dto.UnbanAccountRequestDTO;
import project.service.AccountService;
import project.service.UnbanAccountRequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/unbanRequests")
public class UnbanRequestsController {
    @Autowired
    private UnbanAccountRequestService unbanAccountRequestService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @RequestMapping()
    public String requestsPage(Model model) {

        List<UnbanAccountRequestDTO> unbanAccountRequestDTOS = entityDtoConverter
                .convertUnbanAccountRequestsToUnbanAccountRequestDTOs(unbanAccountRequestService.findByResolved(false));
        log.info("{}", unbanAccountRequestDTOS);
        model.addAttribute("requests", unbanAccountRequestDTOS);
        return "admin/unbanRequests";
    }

    @PostMapping("/refuse")
    public String refuseRequest(@NotNull Long requestId, Model model) {
        try {
            UnbanAccountRequest request = unbanAccountRequestService.findById(requestId)
                    .orElseThrow(() -> new NotFoundException("no such request"));
            unbanAccountRequestService.setResolvedByRequest(true, request);
            log.info("refuse request with id {}", requestId);
            return "redirect:/admin/unbanRequests";
        } catch (NotFoundException e) {
            log.info("no such request with id {}", requestId);
            model.addAttribute("noRequestError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with refuse request with id {}", requestId);
            model.addAttribute("error", true);
        }
        return "/admin/requestAnswerResult";
    }

    @PostMapping("/unban")
    public String unbanRequest(@NotNull Long requestId, Model model) {
        try {
            UnbanAccountRequest request = unbanAccountRequestService.findById(requestId)
                    .orElseThrow(() -> new NotFoundException("no such request"));
            accountService.unbanAndSetResolvedByRequest(false, true, request);
            return "redirect:/admin/unbanRequests";
        } catch (NotFoundException e) {
            log.info("no such request with id {}", requestId);
            model.addAttribute("noRequestError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with refuse request with id {}", requestId);
            model.addAttribute("error", true);
        }
        return "/admin/requestAnswerResult";
    }

    //TODO prg
}
