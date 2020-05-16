package com.muwire.mucats
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class NoSniffInterceptor {
    
    NoSniffInterceptor() {
        match(uri: "/**")
    }

    boolean before() {
        header("X-Content-Type-Options","nosniff") 
        true 
    }

    boolean after() { true }
}
