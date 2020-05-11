<content tag="nav">
    <sec:ifNotLoggedIn>
        <g:link controller="login">Login</g:link>
    </sec:ifNotLoggedIn>
    <sec:ifLoggedIn>
    	<sec:access expression="principal.isAccountNonLocked()">
        	<g:link controller="publish" action="create">Publish</g:link>
        </sec:access>
        <g:link controller="user">Profile</g:link>
        <sec:ifAllGranted roles="ROLE_ADMIN">
            <g:link controller="user" action="list">User List</g:link>
        </sec:ifAllGranted>
        <g:link controller="logout">Logout</g:link>
    </sec:ifLoggedIn>
</content>
