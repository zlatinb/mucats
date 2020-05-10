package com.muwire.mucats

import java.net.http.HttpRequest

import com.muwire.mucats.security.User

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import net.i2p.data.DataHelper

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
        
        def model = [:]
        model['user'] = user
        model['home'] = home
        model['admin'] = admin
        model['canEdit'] = home || admin
        if (!user.profile || user.profile.length() == 0) {
            model['profile'] = message(code : "default.no.profile")
        } else {
            model['profile'] = DataHelper.escapeHTML(user.profile)
        }
        render (view : "show", model : model)
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
