package com.example.demo.Auth.Token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Auth.CustomUser;
import com.example.demo.Auth.CustomUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter{

    private final CustomUserService customUserService;
    private final TokenGenerator tokenGenerator;

    @Autowired
    public TokenFilter(CustomUserService customUserService, TokenGenerator tokenGenerator) {
        this.customUserService = customUserService;
        this.tokenGenerator = tokenGenerator;
    }
    
    private String getTokenFromRequestHeader(HttpServletRequest request)
    {
        String bearer = request.getHeader("Authorization");
        if(bearer.length()>0 && bearer.startsWith("Bearer "))
        {   
            return bearer.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String token = getTokenFromRequestHeader(request);
                if (token != null && tokenGenerator.validateToken(token)) {
                    String userEmail = tokenGenerator.getSubjectFromToken(token);
                    CustomUser customUser = customUserService.loadUserByUsername(userEmail);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
        
    }

}
