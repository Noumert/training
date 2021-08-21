package project.controller;


import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.entity.UnbanAccountRequest;
import project.dto.UnbanAccountRequestDTO;
import project.model.EntityDtoConverter;
import project.service.AccountService;
import project.service.UnbanAccountRequestProcessingService;
import project.service.UnbanAccountRequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Noumert on 11.08.2021.
 */
@Slf4j
@Controller
@RequestMapping("/admin/unbanRequests")
public class UnbanRequestsController {
    @Autowired
    private UnbanAccountRequestProcessingService unbanAccountRequestProcessingService;
    @Autowired
    private UnbanAccountRequestService unbanAccountRequestService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityDtoConverter<UnbanAccountRequest,UnbanAccountRequestDTO> unbanAccountRequestDtoConverter;


    @RequestMapping()
    public String requestsPage(Model model) {

        List<UnbanAccountRequestDTO> unbanAccountRequestDTOS = unbanAccountRequestDtoConverter
                .convertEntityListToDtoList(unbanAccountRequestService.findByResolved(false));
        log.info("{}", unbanAccountRequestDTOS);
        model.addAttribute("requests", unbanAccountRequestDTOS);
        return "admin/unbanRequests";
    }

    @PostMapping("/refuse")
    public String refuseRequest(@NotNull Long requestId, Model model, RedirectAttributes redirectAttributes) {
        try {
            UnbanAccountRequest request = unbanAccountRequestService.findById(requestId)
                    .orElseThrow(() -> new NotFoundException("no such request"));
            unbanAccountRequestService.setResolvedByRequest(true, request);
            log.info("refuse request with id {}", requestId);
            return "redirect:/admin/unbanRequests";
        } catch (NotFoundException e) {
            log.info("no such request with id {}", requestId);
            redirectAttributes.addAttribute("noRequestError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with refuse request with id {}", requestId);
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/admin/unbanRequests/refuse";
    }

    @GetMapping("/refuse")
    public String refuseRequestGet(Model model,
                                   @RequestParam(required = false, defaultValue = "false") Boolean noRequestError,
                                   @RequestParam(required = false, defaultValue = "false") Boolean error) {

        model.addAttribute("noRequestError", noRequestError);
        model.addAttribute("error", error);

        return "/admin/requestAnswerResult";
    }

    @PostMapping("/unban")
    public String unbanRequest(@NotNull Long requestId, Model model,RedirectAttributes redirectAttributes) {
        boolean ban = false;
        try {
            UnbanAccountRequest request = unbanAccountRequestService.findById(requestId)
                    .orElseThrow(() -> new NotFoundException("no such request"));
            unbanAccountRequestProcessingService.unbanAndSetResolvedByRequest(ban, true, request);
            return "redirect:/admin/unbanRequests";
        } catch (NotFoundException e) {
            log.info("no such request with id {}", requestId);
            redirectAttributes.addAttribute("noRequestError", true);
        } catch (RuntimeException e) {
            log.info("something went wrong with refuse request with id {}", requestId);
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/admin/unbanRequests/unban";
    }

    @GetMapping("/unban")
    public String unbanRequestGet(Model model,
                                  @RequestParam(required = false, defaultValue = "false") Boolean noRequestError,
                                  @RequestParam(required = false, defaultValue = "false") Boolean error) {

            model.addAttribute("noRequestError", noRequestError);
            model.addAttribute("error", error);

        return "/admin/requestAnswerResult";
    }
}
