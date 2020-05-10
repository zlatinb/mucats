package com.muwire.mucats.security

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String personaB64
    boolean enabled = true
    boolean accountLocked
    boolean showFullId = true
    String profile

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        username nullable: false, blank: false, unique: true
        personaB64 nullable: false, blank: false, unique: true, size: 512..1024
        profile nullable: true, blank : true, size: 0..32000
    }
}
