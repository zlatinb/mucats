<html>
    <head>
        <title>Challenge</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render template="navbar"/>
    	<p>You are logged in as <sec:username/></p>
    	<p>You have the following roles:</p>
    	<ul>
    		<sec:access expression="hasRole('ROLE_USER')"><li>User</li></sec:access>
    		<sec:access expression="hasRole('ROLE_MODERATOR')"><li>Moderator</li></sec:access>
    		<sec:access expression="hasRole('ROLE_ADMIN')"><li>Admin</li></sec:access>
    	</ul>
        <p>Publish page goes here !</p>
    </body>
</html>
