package com.muwire.mucats

import grails.plugin.springsecurity.annotation.Secured

class PublishController {

    @Secured("hasRole('ROLE_USER')")
    def index() { 
    }
}
