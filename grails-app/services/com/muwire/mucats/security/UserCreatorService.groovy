package com.muwire.mucats.security

import org.springframework.context.annotation.DependsOn

import grails.core.GrailsApplication
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@Transactional
class UserCreatorService {

    GrailsApplication grailsApplication
    RoleService roleService
    UserRoleService userRoleService
    
    /**
     * @param userName to get or create
     * @return the roles for that user name
     */
    public synchronized String[] getOrCreate(String userName, String personaB64) {
        User user = User.where {username == userName}.get()
        if (user == null) {
            Role role = getRoleService().findByAuthority("ROLE_USER")
            user = new User(username : userName, personaB64 : personaB64)
            user.save()
            getUserRoleService().save(user, role)
            return ["ROLE_USER"]
        }
        getUserRoleService().findUserRoles(user).collect {it.role.authority}
    }
    
    UserRoleService getUserRoleService() {
        if (this.userRoleService == null) {
            userRoleService = grailsApplication.mainContext.userRoleService
        }
        userRoleService
    }
    
    RoleService getRoleService() {
        if (this.roleService == null) {
            roleService = grailsApplication.mainContext.roleService
        }
        roleService
    }
}
