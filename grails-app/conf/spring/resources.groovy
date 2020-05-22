import com.muwire.mucats.security.*
import org.springframework.security.authentication.*
import org.springframework.security.web.authentication.*
import org.springframework.security.web.authentication.rememberme.*
import grails.plugin.springsecurity.web.authentication.rememberme.*
import org.springframework.web.servlet.i18n.*

// Place your Spring DSL code here
beans = {

    localeResolver(FixedLocaleResolver, new Locale('en_US'))
    userCreator(UserCreatorService) {
        grailsApplication = ref('grailsApplication')
    }
    
    successHandler(UserCreatingAuthenticationSuccessHandler) {
        userCreator = ref('userCreator')
        userDetailsService = ref('userDetailsService')
    }

    failureHandler(SimpleUrlAuthenticationFailureHandler) {
        defaultFailureUrl = "/login?error=true"
        useForward = false
    }
 
    authenticationProcessingFilter(ChallengeResponseAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        authenticationFailureHandler = ref('failureHandler')
        authenticationSuccessHandler = ref('successHandler')
        rememberMeServices = ref('rememberMeServices')
    }
    challengeResponseAuthenticationProvider(ChallengeResponseAuthenticationProvider)

    userDetailsService(MuCatsUserDetailsService) {
        grailsApplication = ref('grailsApplication')
    }
    tokenRespository(GormPersistentTokenRepository)
    rememberMeServices(PersistentTokenBasedRememberMeServices, 
            "mucats_key",
            ref('userDetailsService'),
            ref('tokenRepository')) 
}
