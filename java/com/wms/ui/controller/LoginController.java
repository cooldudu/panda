package com.wms.ui.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("console")
public class LoginController {
    @GetMapping(value="login")
    String login(){

        return "login";
    }
}