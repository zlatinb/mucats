package com.muwire.mucats

import grails.gorm.services.Service;

@Service(Image)
interface ImageService {
    
    void delete(Long id)
    
}
