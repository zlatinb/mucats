<html>
    <head>
        <title>Challenge</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:if test="${error}">
            ${error}
        </g:if>
        <g:else>
            <g:if test="${home}">
                <p>This is your home page</p>
            </g:if>
            <g:if test="${admin}">
                <p>You are an administrator</p>
            </g:if>
            <h3>${user.username}</h3>
        </g:else>
    </body>
</html>
