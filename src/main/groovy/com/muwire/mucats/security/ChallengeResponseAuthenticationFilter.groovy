package com.muwire.mucats.security


import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

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

        // if we don't have a session, we must have a "personaB64" header
        HttpSession session = request.getSession(false)
        if (session == null) {
            String personaB64 = request.getParameter("personaB64")
            Persona persona
            try {
                persona = new Persona(new ByteArrayInputStream(Base64.decode(personaB64)))
            } catch (Exception badPersona) {
                throw new AuthenticationException("invalid persona", badPersona) {}
            }
            session = request.getSession(true)
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
