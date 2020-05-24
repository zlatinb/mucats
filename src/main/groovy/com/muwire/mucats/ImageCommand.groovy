package com.muwire.mucats

import org.springframework.web.multipart.MultipartFile

import grails.validation.Validateable

class ImageCommand implements Validateable {
    // these are to find the publication
    Long id 
    Integer version
    
    MultipartFile imageFile
    
    static constraints = {
        id nullable : false
        version nullable : false
        imageFile validator : { val, obj, errors ->
            if (val == null)
                return false
            if (val.empty) {
                errors.reject("Cannot be empty")
                return
            }
            
            boolean typeOk = ['jpeg', 'jpg', 'png'].any { 
                val.originalFilename?.toLowerCase()?.endsWith(it)
            }
            
            if (!typeOk) {
                errors.reject("wrong type")
                return false
            }
            true
        }
    }
}
