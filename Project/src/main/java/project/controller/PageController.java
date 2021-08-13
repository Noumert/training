package project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.service.UserService;

@Slf4j
@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/", "/main" })
    public String mainPage(){
        return "index";
    }

}
