package com.example.springsecurityv6.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {



    @GetMapping("user")
    public String welcomeUser(){
        return "Hello USer";
    }
    @GetMapping("admin")
    public String welcomeAdmin(){
        return "Hello Admin";
    }
    @GetMapping("all")
    public String welcomeAll(){
        return "Hello ALl";
    }
}
