package com.muwire.mucats

import grails.plugin.springsecurity.annotation.Secured

class PublishController {

    @Secured(["ROLE_USER"])
    def index() { 
        def model = [:]
        model['shortID'] = session['persona'].getHumanReadableName()
        model['roles'] = "TODO"
        model
    }
}
