package com.muwire.mucats

import grails.gorm.services.Service;

@Service(Publication)
interface PublicationService {
    
    void delete(Long id)
    
    Publication update(Long id, Image image, Boolean featured)
    
    List<Publication> findAllByFeatured(Boolean featured)
}
