package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage(){
//        userService.saveNewUser(User.builder()
//                .email("vova@gmail.com")
//                .password(passwordEncoder.encode("qwer1234"))
//                .firstName("Vova")
//                .lastName("Pavl")
//                .roles(Role.ROLE_USER.name())
//                .build());
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
