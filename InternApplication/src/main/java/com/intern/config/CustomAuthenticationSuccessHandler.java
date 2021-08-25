package com.intern.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
 
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
 
        if (roles.contains("ROLE_ADMIN")) 
        {
            httpServletResponse.sendRedirect("/internapp/admin");
        }
       else if(roles.contains("ROLE_USER"))
        {
            httpServletResponse.sendRedirect("/internapp/user");
        }
       else if(roles.contains("ROLE_MENTOR"))
       {
    	   httpServletResponse.sendRedirect("/internapp/mentorPage");
       }
        else
        {
        	 httpServletResponse.sendRedirect("/internapp/instruction");
        	
        }
    }
}
 