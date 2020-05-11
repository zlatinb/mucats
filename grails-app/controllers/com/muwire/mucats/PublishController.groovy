package com.muwire.mucats

import com.muwire.mucats.security.User

import grails.plugin.springsecurity.annotation.Secured

class PublishController {
    
    static allowedMethods = [save : "POST", comment : "POST"]
    
    def springSecurityService

    @Secured("permitAll")
    def list() {
        int offset = params['offset'] ?: 0
        int max = params['max'] ?: 20
        String sort = params['sort'] ?: "date"
        String order = params['order'] ?: "asc"
        
        def publications = Publication.list(offset : offset, max : max, sort : sort, order : order)
        
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
        
        [publication : publication]
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
}
