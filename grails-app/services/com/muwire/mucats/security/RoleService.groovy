package com.muwire.mucats.security;

import grails.gorm.services.Service;

@Service(Role)
public interface RoleService {

    Role save(String authority);
    Role findByAuthority(String authority);
}
