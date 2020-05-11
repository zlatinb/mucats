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
    
    @Secured("isAuthenticated()")
    def create() {
        [publication : new Publication(params)]
    }
    
    @Secured("isAuthenticated()")
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
    
    @Secured("isAuthenticated()")
    def comment(Long pubId, String commentText) {
        println "adding comment..."
        User me = springSecurityService.getCurrentUser()
        Publication publication = Publication.get(pubId)
        if (!publication) {
            println "no publication?"
            flash.error("No such publication")
            redirect(url : "/")
            return
        }
        println "creating comment"
        Comment comment = new Comment(comment : commentText, user : me, publication : publication)
        comment.validate()
        if (comment.hasErrors()) {
            println "comment has errors"
            flash.error = "Failed to add comment"
            render (view : "show", model : [publication : publication])
            return
        }
        println "about to add to parents"
        publication.addToComments(comment)
        me.addToComments(comment)
        println "saving publication " + publication.save()
        println "saving user " + me.save()
        println "saving comment " + comment.save()
        redirect(action : "show", id : pubId)
    }
}
