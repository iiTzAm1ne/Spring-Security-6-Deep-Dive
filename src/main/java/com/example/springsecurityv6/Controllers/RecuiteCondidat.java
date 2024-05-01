package com.example.springsecurityv6.Controllers;

import com.example.springsecurityv6.Models.Resume;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/recruite/")
public class RecuiteCondidat {



    @GetMapping("process")
    private String Codidatat( @RequestBody Resume cv){
        if(cv.getProfile().equals("JAVA")){
            return "congrat !!!";
        }
        return "sorry we not gonnat accept you !!";
    }
}
