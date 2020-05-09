package com.muwire.mucats

import org.springframework.http.HttpStatus

import net.i2p.crypto.DSAEngine
import net.i2p.data.Base64
import net.i2p.data.Signature
import net.i2p.data.DataHelper

import java.security.SecureRandom
import java.net.Authenticator.RequestorType
import java.nio.charset.StandardCharsets

import com.muwire.core.Persona

import grails.plugin.springsecurity.SpringSecurityUtils

import com.muwire.core.Constants

class LoginController {

    static allowedMethods = [challenge: 'GET', submitresponse: 'POST', index: 'GET']
    
    def springSecurityService

    private final Random random = new SecureRandom()

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: conf.successHandler.defaultTargetUrl
        }
        if (request.getParameter("error") != null) {
            flash.error="Invalid login"
        } 
        render view : "index"
    }

    def challenge() {

        byte[]challenge = new byte[64]
        random.nextBytes(challenge)
        String challengeString = Base64.encode(challenge)

        Persona persona = session['persona']
        session['challenge'] = challengeString.getBytes(StandardCharsets.UTF_8)

        def model = [:]
        model['challenge'] = challengeString
        model['shortID'] = DataHelper.escapeHTML(persona.getHumanReadableName())

        render(view : "challenge", model : model)
    }

    def submitresponse(String response) {
    }
    
    protected ConfigObject getConf() {
        SpringSecurityUtils.securityConfig
    }
}
