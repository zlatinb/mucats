package com.muwire.mucats

import org.springframework.http.HttpStatus

import net.i2p.crypto.DSAEngine
import net.i2p.data.Base64
import net.i2p.data.Signature
import net.i2p.data.DataHelper

import java.security.SecureRandom
import java.nio.charset.StandardCharsets

import com.muwire.core.Persona
import com.muwire.core.Constants

class LoginController {

    static allowedMethods = [challenge: 'GET', submitresponse: 'POST', index: 'GET']

    private final Random random = new SecureRandom()

    def index() {
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
        if (response == null) {
            render status : HttpStatus.NOT_FOUND
            return
        }

        Persona p = session['persona']
        byte[] challenge = session['challenge']
        
        if (p == null || challenge == null) {
            flash.error = "Session expired"
            redirect action : "index"
            return
        }

        boolean ok = false
        byte[]respBytes = Base64.decode(response)
       
        if (respBytes != null) {
            try {
                def sig = new Signature(Constants.SIG_TYPE, respBytes)
                def spk = p.getDestination().getSigningPublicKey()
                ok = DSAEngine.getInstance().verifySignature(sig, challenge, spk)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }

        if(!ok) {
            flash.error = "Invalid response to challenge"
            redirect action : "index"
        } else {
            def model = [:]
            model['shortID'] = DataHelper.escapeHTML(p.getHumanReadableName())
            render(view : "welcome", model : model)
        }
    }
}
