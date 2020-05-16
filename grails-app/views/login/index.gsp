<html>
    <head>
        <title>Login</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>        
        <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
        </g:if>

		<center>
        <h3>Copy-paste your full MuWire ID</h3>
        <g:form useToken="true">
            <g:textArea name="personaB64" rows="10" cols="60"/>
            <br/>
            <g:actionSubmit value="submit" action="submituser" />
        </g:form>
        </center>
        <div class="message">
	        <div><h3>How to find your full MuWire ID:</h3></div>
	        <div>
	        On the desktop client, look at the bottom left for a button called "Copy Full".  Click on that to copy your full ID to the clipboard
	        </div>
	        <div>
	        On the plugin, go to the "About Me" page and click on "Copy To Clipboard"<br/>
	        </div>
        </div>
    </body>
</html>
