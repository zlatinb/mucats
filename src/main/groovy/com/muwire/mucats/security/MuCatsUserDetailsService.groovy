package com.muwire.mucats.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GormUserDetailsService

class MuCatsUserDetailsService extends GormUserDetailsService{

    @Override
    protected UserDetails createUserDetails(Object user, Collection<GrantedAuthority> authorities) {
        def conf = SpringSecurityUtils.securityConfig

        String usernamePropertyName = conf.userLookup.usernamePropertyName
        String enabledPropertyName = conf.userLookup.enabledPropertyName
        String accountLockedPropertyName = conf.userLookup.accountLockedPropertyName

        String username = user."$usernamePropertyName"
        boolean enabled = enabledPropertyName ? user."$enabledPropertyName" : true
        boolean accountLocked = accountLockedPropertyName ? user."$accountLockedPropertyName" : false

        new MuCatsUser(username, enabled,
                !accountLocked, authorities, user.id)
    }

    
}
