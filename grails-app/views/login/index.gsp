<html>
    <head>
        <title>Login</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        
        <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
        </g:if>

        Copy-paste your full MuWire ID
        <g:form>
            <g:textArea name="personaB64" rows="10" cols="60"/>
            <br/>
            <g:actionSubmit value="submit" action="submituser" />
        </g:form>
    </body>
</html>
