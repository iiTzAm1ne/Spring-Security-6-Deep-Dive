package com.example.springsecurityv6.Controllers;


import com.example.springsecurityv6.Models.Accounts;
import com.example.springsecurityv6.dto.Message;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Services.AuthenticationService;
import com.example.springsecurityv6.Services.ManageUserService;
import com.example.springsecurityv6.dto.CreateUserEmployee;
import com.example.springsecurityv6.dto.UpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/manage/")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ManageUserAccount {

    private final  UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher pubEvent;
    private final ManageUserService manageUserService;

    @PostMapping("createEmployee")
    public Message addEmployeeAccount( @RequestBody @Valid CreateUserEmployee request){

        return manageUserService.createUser(request);
    }

    @GetMapping("accounts")
    public Accounts allAccounts(){

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return new Accounts(null,"accounts empty");
        }
        return  new Accounts(users,"200");
    }

    @PutMapping("edit/{id}")
    public Message edit(@RequestBody UpdateRequest user, @PathVariable Long id){
       return manageUserService.changeUSerInfo(user,id);

    }

    @DeleteMapping("delete/{id}")
    public Message allAccounts(@PathVariable Long id){

        return  manageUserService.deleteUser(id);
    }






}
