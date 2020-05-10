<html>
    <head>
        <title>Edit your profile</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
            <h3>Editing ${user.username}</h3>
        </g:else>
    </body>
</html>
