package project.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.exceptions.DuplicatedNumberException;
import project.service.CreditCardService;


@Controller
@RequestMapping("/user/creditCards")
public class CreditCardsController {
    @Autowired
    CreditCardService creditCardService;

        @RequestMapping()
        public String creditCardsPage(Model model){
            try {
                model.addAttribute("userCards",creditCardService.findCurrentUserCards());
            } catch (NotFoundException | UnexpectedRollbackException e) {
                model.addAttribute("error",true);
            }
            return "user/creditCards";
        }

        @PostMapping("/add")
        public String addCreditCard(Model model){
            try {
                creditCardService.saveNewCard();
                model.addAttribute("success",true);
            } catch (NotFoundException | DuplicatedNumberException | UnexpectedRollbackException e) {
                model.addAttribute("error",true);
            }
            return "/user/cardAddingResult";
        }
}
