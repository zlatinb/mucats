package com.muwire.mucats.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import grails.gorm.transactions.Transactional

@Transactional
class UserCreatingAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException {

        ChallengeResponseAuthentication cra = authentication
        String userName = cra.getPersona().getHumanReadableName()
        User user = User.where { username == userName }.get()
        if (user == null) {
            user = new User(username : userName)
            user.save()
            Role role = Role.where { authority == "ROLE_USER"}.get()
            if (role == null) {
                role = new Role(authority : "ROLE_USER")
                role.save()
            }
            UserRole userRole = new UserRole(user : user, role : role)
            userRole.save()
        }
            
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
