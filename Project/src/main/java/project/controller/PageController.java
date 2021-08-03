package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.entity.User;
import project.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
public class PageController {
    @Autowired
    UserService userService;

    @RequestMapping(value = { "/", "/main" })
    public String mainPage(){
        return "index";
    }

}
