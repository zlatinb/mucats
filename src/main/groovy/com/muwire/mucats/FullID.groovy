package com.muwire.mucats

import com.muwire.core.Persona

import net.i2p.data.Base64

import grails.validation.Validateable

class FullID implements Validateable {

    String personaB64

    static constraints = {
        personaB64 blank:false, validator : {val, obj ->
            try {
                Persona p = new Persona(new ByteArrayInputStream(Base64.decode(val)))
                return true
            } catch (Exception e) {
                e.printStackTrace()
                return false
            }
        }
    }

    static Persona getPersona(FullID fullID) {
        try {
            return new Persona(new ByteArrayInputStream(Base64.decode(fullID.personaB64)))
        } catch (Exception bad) {
            return null
        }
    }
}
