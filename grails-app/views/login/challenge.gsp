<html>
    <head>
        <title>Challenge</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        Prove that you are <span class="short-id">${shortID}</span><br/>
        Sign the following challenge:<br>
        <div id="challenge">${challenge}</div>
        <g:form>
            <g:textField name="response"/><br/>
            <input type="checkbox" name="remember-me" <g:if test="${hasCookie}">checked</g:if> /> Remember me</br>
            <g:actionSubmit value="Submit" action="submitresponse"/>
        </g:form>
    </body>
</html>
