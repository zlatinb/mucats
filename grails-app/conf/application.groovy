



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
grails.plugin.springsecurity.adminUser = "zlatinb@3k2gijdfdcuczkfypfddj4qsnnf744mj"
grails.plugin.springsecurity.adminB64 = "AQAHemxhdGluYiN~3G-hPoBfJ04mhcC52lC6TYSwWxH-WNWno9Y35JS-WrXlnPsodZtwy96ttEaiKTg-hkRqMsaYKpWar1FwayR6qlo0pZCo5pQOLfR7GIM3~wde0JIBEp8BUpgzF1-QXLhuRG1t7tBbenW2tSgp5jQH61RI-c9flyUlOvf6nrhQMZ3aoviZ4aZW23Fx-ajYQBDk7PIxuyn8qYNwWy3kWOhGan05c54NnumS3XCzQWFDDPlADmco1WROeY9qrwwtmLM8lzDCEtJQXJlk~K5yLbyB63hmAeTK7J4iS6f9nnWv7TbB5r-Z3kC6D9TLYrQbu3h4AAxrqso45P8yHQtKUA4QJicS-6NJoBOnlCCU887wx2k9YSxxwNydlIxb1mZsX65Ke4uY0HDFokZHTzUcxvfLB6G~5JkSPDCyZz~2fREgW2-VXu7gokEdEugkuZRrsiQzyfAOOkv53ti5MzTbMOXinBskSb1vZyN2-XcZNaDJvEqUNj~qpfhe-ov2F7FuwQUABAAHAAAfqq-MneIqWBQY92-sy9Z0s~iQsq6lUFa~sYMdY-5o-94fF8a140dm-emF3rO8vuidUIPNaS-37Rl05mAKUCcB"
grails.plugin.springsecurity.rememberMe.persistent = true
grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'com.muwire.mucats.security.PersistentLogin'

