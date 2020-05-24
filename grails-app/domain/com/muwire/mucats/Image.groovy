package com.muwire.mucats

class Image {
    
    byte [] data
    String type

    static constraints = {
        data maxSize : 1024 * 1024
    }
    
    static belongsTo = [publication : Publication]
}
