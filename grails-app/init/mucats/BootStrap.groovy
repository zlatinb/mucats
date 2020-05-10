package mucats

import org.springframework.beans.factory.annotation.Autowired

import com.muwire.mucats.security.Role
import com.muwire.mucats.security.RoleService
import com.muwire.mucats.security.User
import com.muwire.mucats.security.UserRoleService
import com.muwire.mucats.security.UserService

import grails.compiler.GrailsCompileStatic
import grails.config.Config
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityUtils

@GrailsCompileStatic
class BootStrap {
    
    RoleService roleService
    UserService userService
    UserRoleService userRoleService

    def init = { servletContext ->
        
        List<String> authorities = ['ROLE_USER','ROLE_MODERATOR','ROLE_ADMIN']
        authorities.each {
            if ( !roleService.findByAuthority(it))
                roleService.save(it)
        }
        
        
        ConfigObject obj = SpringSecurityUtils.securityConfig
        String adminUserName = obj.getProperty('adminUser')
        String adminUserB64 = obj.getProperty('adminB64')
        User adminUser = userService.findByUsername(adminUserName)
        if (adminUser == null) {
            adminUser = new User(username : adminUserName, personaB64 : adminUserB64)
            userService.save(adminUser)
            authorities.each { 
                Role role = roleService.findByAuthority(it)
                userRoleService.save(adminUser, role)
            }
        }
    }
    
    def destroy = {
    }
}
