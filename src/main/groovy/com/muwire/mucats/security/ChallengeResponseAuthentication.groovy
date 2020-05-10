package com.muwire.mucats.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority

import com.muwire.core.Persona

class ChallengeResponseAuthentication implements Authentication {

    final Persona persona
    final byte[] challenge
    final String response
    
    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>()
    private volatile boolean authenticated
    private volatile MuCatsUser principal
    
    public ChallengeResponseAuthentication(Persona persona, byte[] challenge, String response) {
        this.persona = persona
        this.challenge = challenge
        this.response = response
    }

    @Override
    public String getName() {
        persona.getHumanReadableName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        principal
    }
    
    public void setPrincipal(MuCatsUser principal) {
        this.principal = principal
    }

    @Override
    public boolean isAuthenticated() {
        authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated
    }
    
    public void setRoles(String[]roles) {
        roles.collect(authorities, {new SimpleGrantedAuthority(it)})
    }
}
