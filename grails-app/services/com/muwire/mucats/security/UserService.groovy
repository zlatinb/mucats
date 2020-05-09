package com.muwire.mucats.security;

import grails.gorm.services.Service;

@Service(User)
public interface UserService {

    User findByUsername(String username);
    
    User save(User user);
}
