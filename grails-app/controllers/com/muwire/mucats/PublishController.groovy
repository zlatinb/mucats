package com.muwire.mucats

import org.springframework.dao.DataIntegrityViolationException

import com.muwire.mucats.security.Role
import com.muwire.mucats.security.RoleService
import com.muwire.mucats.security.User
import com.muwire.mucats.security.UserRoleService

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

class PublishController {
    
    static allowedMethods = [save : "POST", saveEdited: "POST", comment : "POST", delete : "POST", 
        feature : "POST", unfeature : "POST"]
    
    def springSecurityService
    RoleService roleService
    PublicationService publicationService
    ImageService imageService

    @Secured("permitAll")
    def list() {
        if (!params['offset'])
            params['offset'] = 0
        if (!params['max'] || Integer.parseInt(params['max']) > 20)
            params['max'] = 20
        if (!params['sort'])
            params['sort'] = "date"
        if (!params['order'])
            params['order'] = "desc"
        if (!params['q'])
            params['q'] = ""
        
        
        List<Publication> featured = publicationService.findAllByFeatured(true)
        
        featured.sort({l, r ->
            long ld = l.featuredDate != null ? l.featuredDate : 0
            long rd = r.featuredDate != null ? r.featuredDate : 0
            return Long.compare(rd, ld)
        })
                
        def publications
        int total
        if (params['q'].trim().length() > 0) {
            if (!springSecurityService.isLoggedIn()) {
                flash.error = "You need to log in to search"
                redirect(url: "/")
                return
            }
            if (!springSecurityService.getPrincipal().isAccountNonLocked()) {
                flash.error = "Your account is locked"
                redirect(url : "/")
                return
            }
            String q = params['q']
            def query = Publication.where {
                ( name =~ "%$q%" ) || (description =~ "%$q%")
            }
            publications = query.list(params)
            total = query.count()
        } else {       
            publications = Publication.list(params)
            total = Publication.count()
        }
        
        [publications : publications, total : total, q : params.q, featured : featured]
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
        withForm {
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
        }.invalidToken {
            flash.error = "Duplicate submission"
            render(view : "create", model : [])
            return
        }
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def edit(long id) {
        Publication publication = Publication.get(id)
        if (publication == null) {
            flash.error = "No such publication"
            redirect (url : "/")
            return
        }
        
        if (!canEdit(publication))
            return
        
        [publication : publication]
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    @Transactional
    def saveEdited(long pubId, String description) {
        withForm {
            Publication publication = Publication.get(pubId)
            if (publication == null) {
                flash.error = "No such publication"
                redirect (url : "/")
                return
            }
            
            if (!canEdit(publication))
                return
            
            publication.description = description
            publication.validate()
            if (publication.hasErrors()) {
                flash.error = "Invalid submission"
                render(view : "create", model : [publication : publication])
                return
            }
            publication.lastEdited = new Date()
            publication.save(flush : true)
            redirect(action : "show", id : publication.id)
        }.invalidToken {
            flash.error = "Duplicate submission"
            render(view : "list")
            return
        }
    }
    
    private boolean canEdit(Publication publication) {
     
        boolean canEdit = isModerator() || publication.user.id == me.id
        
        if (!canEdit) {
            flash.error = "You can't edit this publication"
            redirect (url : "/")
            return false
        }
        true
    }
    
    private boolean isModerator() {
        User me = springSecurityService.getCurrentUser()
        Role moderator = roleService.findByAuthority("ROLE_MODERATOR")
        me.getAuthorities().contains(moderator)
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def uploadImage(ImageCommand command) {
        withForm {
            if (command == null) {
                flash.error = "No such publication"
                redirect(url: "/")
                return
            }
            
            Publication publication = Publication.get(command.id)
            if (publication == null) {
                flash.error = "No such publication"
                redirect (url : "/")
                return
            }
            
            
            if (command.hasErrors()) {
                respond(command.errors, model : [publication : publication], view : "edit")
                return
            }
            
            if (!canEdit(publication))
                return
            
            Image image = new Image(data : command.imageFile.bytes, type: command.imageFile.contentType)
            
            publication = publicationService.update(command.id, image, publication.featured)
            if (publication.hasErrors()) {
                respond(command.errors, model : [publication : publication], view : "edit")
                return
            }
            
            redirect(action : "show", id : publication.id)
            
        }.invalidToken {
            flash.error = "Duplicate submission"
            render(view : "list")
        }
    }
    
    @Secured("permitAll")
    def image(Long id) {
        Image image = Image.get(id)
        if (!image) {
            flash.error = "No such image"
            redirect (url : "/")
            return
        }
        render file:image.data, contentType:image.type
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def comment(Long pubId, String commentText) {
        withForm {
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
        }.invalidToken {
            flash.error = "Duplicate submission"
            render (view : "show", model : [publication : publication])
            return
        }
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def delete(Long pubId) {
        Publication publication = Publication.get(pubId)
        if (!publication) {
            flash.error("No such publication")
            redirect(url : "/")
            return
        }
        if (!canEdit(publication))
            return
        try {
            publicationService.delete(pubId)
            redirect(action : "list")
        } catch (DataIntegrityViolationException e) {
            flash.message = "Could not delete publication"
            redirect(action : "show", id : pubId)
        }
    }
    
    @Secured("isAuthenticated() && principal.isAccountNonLocked()")
    def deleteImage(Long pubId) {
        Publication publication = Publication.get(pubId)
        if (!publication || !publication.image) {
            flash.error("No such publication/image")
            redirect(url : "/")
            return
        }
        if (!canEdit(publication))
            return
        try {
            Long id = publication.image.id
            publication.image = null
            imageService.delete(id)
            publicationService.update(pubId, null, publication.featured)
        } catch (DataIntegrityViolationException e) {
            flash.message = "Could not delete image"
        } finally {
            redirect(action : "show", id : pubId)
        }
    }
    
    @Secured('ROLE_MODERATOR')
    @Transactional
    def feature(Long pubId) {
        withForm {
            Publication publication = Publication.get(pubId)
            if (publication == null) {
                flash.error = "No such publication"
                redirect (url : "/")
                return
            }
            boolean wasFeatured = publication.featured
            publication = publicationService.update(pubId, Publication.get(pubId).image, true)
            if (!wasFeatured) {
                publication.featuredDate = System.currentTimeMillis()
                publication.save()
            }
            redirect(action : "list")
        }.invalidToken {
            flash.error = "Duplicate submission"
            render (view : "show", model : [publication : publication])
            return
        }
    }
    
    @Secured('ROLE_MODERATOR')
    def unfeature(Long pubId) {
        withForm {
            Publication publication = publicationService.update(pubId, Publication.get(pubId).image, false)
            if (publication == null) {
                flash.error = "No such publication"
                redirect (url : "/")
                return
            }
            redirect(action : "list")
        }.invalidToken {
            flash.error = "Duplicate submission"
            render (view : "show", model : [publication : publication])
            return
        }
    }
}
