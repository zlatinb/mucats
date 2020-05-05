package com.muwire.mucats

import org.springframework.http.HttpStatus

import net.i2p.data.Base64

import java.security.SecureRandom

class LoginController {

    static allowedMethods = [submituser: 'POST', index: 'GET']

    private final Random random = new SecureRandom()

    def index() {
    }

    public def submituser(FullID user) {
        if (user == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        if (user.hasErrors()) {
            user.errors.allErrors.each {
                println it
            }
            return
        }

        byte[]challenge = new byte[64]
        random.nextBytes(challenge)
        String challengeString = Base64.encode(challenge)

        session['challenge'] = challengeString
        session['user'] = user

        def model = [:]
        model['challenge'] = challengeString
        model['shortID'] = FullID.getPersona(user).getHumanReadableName()

        render(view : "challenge", model : model)
    }
}
