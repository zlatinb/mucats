package com.muwire.mucats

import java.net.http.HttpRequest

import com.muwire.mucats.security.User

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

class UserController {
    
    def springSecurityService

    @Secured("isAuthenticated()")
    def index() {
        def user = springSecurityService.loadCurrentUser()
        redirect (action : "show", id : user.id)
    }
    
    @Secured("permitAll")
    def show(Long id) {
        boolean home = false
        boolean admin = false
        if (springSecurityService.isLoggedIn()) {
            def viewer = springSecurityService.loadCurrentUser()
            home = viewer.id == id
            
            admin = SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")
        }
        
        def user = User.get(id)
        if (!user) {
            def error = message(code : 'default.not.found.message', args: [message(code: 'person.label', default : 'Person'), id])
            render( view : "show", model : [error : error])
            return 
        }
        
        render (view : "show", model : [user : user, home : home, admin : admin])
    }
    
    @Secured("isAuthenticated()")
    def edit(Long id) {
        
        boolean canEdit = springSecurityService.loadCurrentUser().id == id
        canEdit |= SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")
        
        if (!canEdit) {
            def error = message(code : "default.cannot.edit.message")
            render (view : "edit", model : [error : error])
            return
        }
        
        def user = User.get(id)
        if (!user) {
            def error = message(code : 'default.not.found.message', args: [message(code: 'person.label', default : 'Person'), id])
            render( view : "edit", model : [error : error])
            return 
        }
        render(view : "edit", model : [user : user])
    }
}
