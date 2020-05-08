package com.muwire.mucats.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

import com.muwire.core.Constants

import net.i2p.crypto.DSAEngine
import net.i2p.data.Base64
import net.i2p.data.Signature

class ChallengeResponseAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ChallengeResponseAuthentication cra = authentication
        
        byte [] response = Base64.decode(cra.getResponse())
        if (response == null)
            throw new AuthenticationException("base64 couldn't decode response") {}
        
        def sig = new Signature(Constants.SIG_TYPE, response)
        def spk = cra.getPersona().getDestination().getSigningPublicKey()
        if (DSAEngine.getInstance().verifySig(sig, cra.getChallenge(), spk)) {
            authentication.setAuthenticated(true)
            cra.setRoles("user") // TODO: check with db and stuff
            return cra
        }else
            throw new AuthenticationException("invalid response") {}
    }

}
