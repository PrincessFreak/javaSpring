package com.example.unistore.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/users/userAuthorization")
    public String userLogin() {
    return "/users/userAuthorization";
    }


}
