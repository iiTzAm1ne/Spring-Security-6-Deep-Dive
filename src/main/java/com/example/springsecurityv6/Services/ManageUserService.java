package com.example.springsecurityv6.Services;

import com.example.springsecurityv6.Models.*;
import com.example.springsecurityv6.Repositories.RetrieveAccountRepository;
import com.example.springsecurityv6.Repositories.TokenRepository;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Repositories.VerificationTokenRepository;
import com.example.springsecurityv6.dto.CreateUserEmployee;
import com.example.springsecurityv6.dto.Message;
import com.example.springsecurityv6.dto.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ManageUserService {

    private final UserRepository userRepository ;
    private final TokenRepository tokenRepository ;
    private final VerificationTokenRepository verificationTokenRepository ;
    private final RetrieveAccountRepository retrieveAccountRepository ;

    private  final PasswordEncoder passwordEncoder;

    public Message createUser(CreateUserEmployee request){

       Optional<User> u = userRepository.findByEmail(request.getEmail());
        if(u.isPresent()){
            return new Message("employee alredy exist in Db","404");
        }
        if(!request.getPassword().equals(request.getConfirmPassword())){
            return new Message("passwords do not match","404");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .age(request.getAge())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRole())
                .createdAt(Instant.now())
                .account_state(request.isAccount_state())
                .build();

        userRepository.save(user);
        return new Message("Employee Account successfully created","200");

    }

    public Message changeUSerInfo(UpdateRequest   updateRequest,  Long id ){

        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return new Message("user not exist","400");
        }
        user.setFullName(updateRequest.getFullName());
        user.setEmail(updateRequest.getEmail());
        user.setAccount_state(updateRequest.isAccount_state());
        user.setRoles(updateRequest.getRole());
        if(!user.getPassword().equals(passwordEncoder.encode(updateRequest.getPassword())))
        {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        System.out.println("updateddddddd");
        userRepository.save(user);
        return new Message("user updated","200");
    }


    public Message deleteUser(Long id){


        userRepository.deleteById(id);
        return new Message("user deleted","200");
    }
}
