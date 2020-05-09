package com.muwire.mucats.security

import org.springframework.security.core.GrantedAuthority

import grails.plugin.springsecurity.userdetails.GrailsUser

/**
 * A user object that doesn't have a password
 */
class MuCatsUser extends GrailsUser {

    public MuCatsUser(String username, boolean enabled,
            boolean accountNonLocked, Collection<GrantedAuthority> authorities,
            Object id) {
        super(username, "", enabled, true, true, accountNonLocked, authorities, id);
    }

}
