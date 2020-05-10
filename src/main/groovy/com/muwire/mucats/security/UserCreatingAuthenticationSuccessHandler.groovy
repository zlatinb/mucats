package com.muwire.mucats.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.context.annotation.Bean
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

class UserCreatingAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    UserCreatorService userCreator
    UserDetailsService userDetailsService
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException {

        ChallengeResponseAuthentication cra = authentication
        String userName = cra.getPersona().getHumanReadableName()
        String personaB64 = cra.getPersona().toBase64()
        String [] roles = userCreator.getOrCreate(userName, personaB64)
        cra.setRoles(roles)
        def details = userDetailsService.loadUserByUsername(userName)
        cra.setPrincipal(details)
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
