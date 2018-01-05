package com.peterjurkovic.travelagency.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
