package com.muwire.mucats.security;

import java.util.List;

import grails.gorm.services.Service;

@Service(UserRole)
public interface UserRoleService {
    UserRole save(User user, Role role);
    List<UserRole> findUserRoles(User user);
    void delete(User user)
}
