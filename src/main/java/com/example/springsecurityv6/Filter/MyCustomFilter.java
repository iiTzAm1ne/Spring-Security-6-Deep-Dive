package com.example.springsecurityv6.Filter;


import com.example.springsecurityv6.Jwt.JwtService;
import com.example.springsecurityv6.Models.Token;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Models.UserDetailsServiceImpl;
import com.example.springsecurityv6.Repositories.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class MyCustomFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //extract header from request
        String header = request.getHeader("Authorization");
        //validate header if null or not

        if(header ==null || !header.startsWith("Bearer ") ){

            filterChain.doFilter(request,response);
            return;


        }
        //extract token from header
        String token =header.substring(7);

        System.out.println("this is token =>" +token);
        //extract email from token
        String username = jwtService.extractUsername(token);

        //verify existing user
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = (User) userDetailsService.loadUserByUsername(username);
            Token userToken = tokenRepository.findByToken(token);
            //validate token and verify expiration

            if(jwtService.validateToken(token,user) && !userToken.isIsexpired() && !userToken.isIsinvoked()){
                //create Authentication Object and pass it to the next filter

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,null,user.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        //got to the next filter
        filterChain.doFilter(request,response);
    }
}



























