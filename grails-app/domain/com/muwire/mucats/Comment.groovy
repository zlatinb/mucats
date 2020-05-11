package com.muwire.mucats

import com.muwire.mucats.security.User

class Comment {

    String comment
    Date date = new Date()
    
    static constraints = {
        comment nullable : false, blank : false, size: 0..1024
    }
    
    static belongsTo = [user : User, publication : Publication]
}
