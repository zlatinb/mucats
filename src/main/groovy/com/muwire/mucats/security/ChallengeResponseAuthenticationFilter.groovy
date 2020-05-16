package com.muwire.mucats.security


import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.grails.web.servlet.mvc.SynchronizerTokensHolder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import com.muwire.core.Persona

import net.i2p.data.Base64

class ChallengeResponseAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    public ChallengeResponseAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/*","POST"))
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException, IOException, ServletException {

        HttpSession session = request.getSession()
        SynchronizerTokensHolder tokensHolder = session.getAttribute("SYNCHRONIZER_TOKENS_HOLDER")
        if (!tokensHolder)
            throw new AuthenticationException("inconsistent session") {}
        String url = request.getParameter("SYNCHRONIZER_URI")
        String token = request.getParameter("SYNCHRONIZER_TOKEN")
        boolean valid = false
        if (url && token) {
            valid = tokensHolder.isValid(url, token)
            tokensHolder.resetToken(url,token)
        }
        
        if (!valid) {
            response.sendError(403,"Duplicate form submission")
            return null
        }
        
        String personaB64 = request.getParameter("personaB64")
        if (personaB64 != null) {
            Persona persona
            try {
                persona = new Persona(new ByteArrayInputStream(Base64.decode(personaB64)))
            } catch (Exception badPersona) {
                throw new AuthenticationException("invalid persona", badPersona) {}
            }
            session.setAttribute("persona", persona)
            response.sendRedirect("/login/challenge")
            return null
        } else {
            // we must have a persona and challenge attributes and a "response" parameter
            Persona persona = session.getAttribute("persona")
            byte[] challenge = session.getAttribute("challenge")
            String answer = request.getParameter("response")
            if (persona == null || answer == null || challenge == null)
                throw new AuthenticationException("inconsistent session") {} 
            def cra = new ChallengeResponseAuthentication(persona, challenge, answer)
            return getAuthenticationManager().authenticate(cra)
        }
    }
}
