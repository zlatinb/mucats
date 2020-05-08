import com.muwire.mucats.security.*
import org.springframework.security.authentication.*

// Place your Spring DSL code here
beans = {
 
    authenticationProcessingFilter(ChallengeResponseAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
    }
    challengeResponseAuthenticationProvider(ChallengeResponseAuthenticationProvider)
}
