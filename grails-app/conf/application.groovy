

// mask the default spring login controller
grails.plugin.springsecurity.auth.loginFormUrl="/login"
grails.plugin.springsecurity.logout.postOnly=false


grails.plugin.springsecurity.providerNames = [
        'challengeResponseAuthenticationProvider',
        'anonymousAuthenticationProvider',
        'rememberMeAuthenticationProvider']

// Added by the Spring Security Core plugin:

grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.muwire.mucats.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.muwire.mucats.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.muwire.mucats.security.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
    [pattern: '/login/auth',     access: ['denyAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.rememberMe.cookieName = "mucats_remember_me"
grails.plugin.springsecurity.rememberMe.key = "mucats_key"
grails.plugin.springsecurity.rememberMe.persistent = true
grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'com.muwire.mucats.security.PersistentLogin'

