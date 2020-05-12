package com.muwire.mucats

import org.springframework.dao.DataIntegrityViolationException

import com.muwire.mucats.security.Role
import com.muwire.mucats.security.RoleService
import com.muwire.mucats.security.User
import com.muwire.mucats.security.UserRoleService

import grails.plugin.springsecurity.annotation.Secured

class PublishController {
    
    static allowedMethods = [save : "POST", comment : "POST", delete : "POST"]
    
    def springSecurityService
    RoleService roleService
    PublicationService publicationService

    @Secured("permitAll")
    def list() {
        if (!params['offset'])
            params['offset'] = 0
        if (!params['max'])
            params['max'] = 20
        if (!params['sort'])
            params['sort'] = "date"
        if (!params['order'])
            params['order'] = "desc"
                
        def publications = Publication.list(params)
        
        [publications : publications, total : Publication.count()]
    }
    
    @Secured("permitAll")
    def show(int id) {
        Publication publication = Publication.get(id)
        if (!publication) {
            flash.error = "No such publication"
            redirect(url: "/")
            return
        }
        
        boolean canDelete = false
        if (springSecurityService.isLoggedIn()) {
            User me = springSecurityService.getCurrentUser()
            if (!me.isAccountLocked()) {
                Role moderator = roleService.findByAuthority("ROLE_MODERATOR")
                canDelete |= me.getAuthorities().contains(moderator)
                canDelete |= publication.user.id == me.id  
            }
        }
        
        [publication : publication, canDelete : canDelete]
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def create() {
        [publication : new Publication(params)]
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def save(String hash, String name, String description) {
        User me = springSecurityService.getCurrentUser()
        Publication publication = new Publication(hash : hash, name : name, description : description, user : me)
        publication.validate()
        if (publication.hasErrors()) {
            flash.error = "Invalid submission"
            render(view : "create", model : [publication : publication])
            return
        }
        publication.save()
        redirect(action : "show", id : publication.id)
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def comment(Long pubId, String commentText) {
        User me = springSecurityService.getCurrentUser()
        Publication publication = Publication.get(pubId)
        if (!publication) {
            flash.error("No such publication")
            redirect(url : "/")
            return
        }
        Comment comment = new Comment(comment : commentText, user : me, publication : publication)
        comment.validate()
        if (comment.hasErrors()) {
            flash.error = "Failed to add comment"
            render (view : "show", model : [publication : publication])
            return
        }
        publication.addToComments(comment)
        me.addToComments(comment)
        publication.save()
        me.save()
        comment.save()
        redirect(action : "show", id : pubId)
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def delete(Long pubId) {
        Publication publication = Publication.get(pubId)
        if (!publication) {
            flash.error("No such publication")
            redirect(url : "/")
            return
        }
        User me = springSecurityService.getCurrentUser()
        Role moderator = roleService.findByAuthority("ROLE_MODERATOR")
        boolean canDelete = me.getAuthorities().contains(moderator)
        canDelete |= publication.user.id == me.id
        
        if (!canDelete) {
            flash.error("You can't delete this publication")
            redirect(url : "/")
            return
        }
        try {
            publicationService.delete(pubId)
            redirect(action : "list")
        } catch (DataIntegrityViolationException e) {
            flash.message = "Could not delete publication"
            redirect(action : "show", id : pubId)
        }
        
    }
}
