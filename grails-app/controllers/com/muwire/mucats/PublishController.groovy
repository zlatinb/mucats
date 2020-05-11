package com.muwire.mucats

import com.muwire.mucats.security.User

import grails.plugin.springsecurity.annotation.Secured

class PublishController {
    
    static allowedMethods = [save : "POST"]
    
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
        println "created publication"
        publication.validate()
        if (publication.hasErrors()) {
            println "publication has errors"
            flash.error = "Invalid submission"
            render(view : "create", model : [publication : publication])
            return
        }
        publication.save()
        println "saved publication"
        redirect(action : "show", id : publication.id)
    }
}
