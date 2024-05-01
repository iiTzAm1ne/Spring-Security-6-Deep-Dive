package com.example.springsecurityv6.Controllers;


import com.example.springsecurityv6.Event.RegistrationCompleteEvent;
import com.example.springsecurityv6.Models.*;
import com.example.springsecurityv6.Repositories.VerificationTokenRepository;
import com.example.springsecurityv6.Services.AuthenticationService;
import com.example.springsecurityv6.Services.TokenVerificationService;
import com.example.springsecurityv6.dto.AuthenticationRequest;
import com.example.springsecurityv6.dto.Message;
import com.example.springsecurityv6.dto.RegisterRequest;
import com.example.springsecurityv6.dto.VerifyCodeRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher pubEvent;
    private final VerificationTokenRepository tokenRepository ;
    private final TokenVerificationService tokenVerificationService;

    @PostMapping("register")
    public Message register(@RequestBody RegisterRequest auth, final HttpServletRequest request){
        User user = authenticationService.register(auth);
        pubEvent.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return new Message(" Please, check your email for to complete your registration","200");

    }

    @PostMapping("auth")
    public Message login(@RequestBody AuthenticationRequest auth){

        return authenticationService.Authenticate(auth);

    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        System.out.println("verify");
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isAccount_state()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = tokenVerificationService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            tokenRepository.deleteById(theToken.getId());
            return "Email verified successfully. Now you can login to your account"+authenticationService.activateAccount(theToken.getUser());
        }
        return "Invalid verification token";
    }




    @GetMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String email){
        return authenticationService.forgetPassword(email);
    }

    @GetMapping("verify-code")

    public ResponseEntity<?> verifyCodeTorenewPAssword(@RequestBody VerifyCodeRequest request){
        return authenticationService.verifyCode(request);
    }

    @PostMapping("new-password")

    public ResponseEntity<?> RetrieveAccount_NewPassword(@RequestBody ChangePassword request){
        return authenticationService.addNewPassword(request);
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

}
