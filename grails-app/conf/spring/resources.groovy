import com.muwire.mucats.security.*
import org.springframework.security.authentication.*
import org.springframework.security.web.authentication.*

// Place your Spring DSL code here
beans = {

    failureHandler(SimpleUrlAuthenticationFailureHandler) {
        defaultFailureUrl = "/login?error=true"
        useForward = false
    }
 
    authenticationProcessingFilter(ChallengeResponseAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        authenticationFailureHandler = ref('failureHandler')
    }
    challengeResponseAuthenticationProvider(ChallengeResponseAuthenticationProvider)
}
