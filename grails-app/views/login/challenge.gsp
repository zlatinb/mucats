<html>
    <head>
        <title>Challenge</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        Prove that you are <span class="short-id">${shortID}</span><br/>
        Sign the following challenge:<br>
        <div id="challenge">${challenge}</div>
        <g:form>
            <g:textField name="response"/>
            <g:actionSubmit value="Submit" action="submitresponse"/>
        </g:form>
    </body>
</html>
