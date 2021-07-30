package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.Roles;
import project.entity.User;
import project.repository.UserRepository;
import project.service.UserService;

@Controller
public class LoginController {
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String loginPage(){
//        userService.saveNewUser(User.builder()
//                .email("vova@gmail.com")
//                .password(passwordEncoder.encode("qwer1234"))
//                .firstName("Vova")
//                .lastName("Pavl")
//                .roles(Roles.ROLE_USER.name())
//                .build());
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
