package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user/creditCards")
public class CreditCardsController {

        @RequestMapping()
        public String creditCardsPage(){
            return "user/creditCards";
        }

        @PostMapping("/add")
        public String addCreditCard(){
            return "redirect:/user/creditCards";
        }
}
