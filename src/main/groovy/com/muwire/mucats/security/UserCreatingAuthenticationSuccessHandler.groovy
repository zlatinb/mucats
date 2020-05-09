package com.muwire.mucats.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.context.annotation.Bean
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

class UserCreatingAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    UserCreatorService userCreator
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException {

        ChallengeResponseAuthentication cra = authentication
        String userName = cra.getPersona().getHumanReadableName()
        String [] roles = userCreator.getOrCreate(userName)
        cra.setRoles(roles)
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
