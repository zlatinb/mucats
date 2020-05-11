<html>
    <head>
        <title>Publication</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
            <h3>${publication.name}</h3>
            <p>Hash: ${publication.hash}</p>
            <p>Description:</p>
            <pre>${publication.description}</p>
        </g:else>
    </body>
</html>
