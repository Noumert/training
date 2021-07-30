package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = { "/", "/main" })
    public String mainPage(){
        return "index";
    }

    @RequestMapping(value = { "/test" })
    public String testPage(){
        return "index";
    }
}
