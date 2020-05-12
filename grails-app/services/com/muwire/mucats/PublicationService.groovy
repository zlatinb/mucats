package com.muwire.mucats

import grails.gorm.services.Service;

@Service(Publication)
interface PublicationService {
    
    void delete(Long id)
}
