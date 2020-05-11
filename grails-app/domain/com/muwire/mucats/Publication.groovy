package com.muwire.mucats

import com.muwire.core.InfoHash
import com.muwire.mucats.security.User

import net.i2p.data.Base64

class Publication {
    
    String hash
    String name
    String description
    Date date = new Date()

    static constraints = {
        name nullable : false, blank : false, size: 1..128
        description nullable : true, blank : true, size: 0..32000
        hash blank : false, unique : true, validator : {val, obj, errors -> 
            try {
                new InfoHash(Base64.decode(val))
                return true
            } catch (Exception badHash) {
                errors.reject("default.badHash")
                return false
            }
        }
    }
    
    static belongsTo = [user : User]
    
    static hasMany = [comments : Comment]
    
    static mapping = {
        comments lazy: false
    }
}