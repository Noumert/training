package project.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
            return "user/creditCards";
        }

        @PostMapping("/add")
        public String addCreditCard(Model model){
            try {
                creditCardService.saveNewCard();
                model.addAttribute("success",true);
            } catch (NotFoundException | DuplicatedNumberException e) {
                model.addAttribute("error",true);
                model.addAttribute("success",false);
            }
            return "/user/cardAddingResult";
        }
}
