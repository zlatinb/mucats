<html>
    <head>
        <title>Challenge</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <center>
        Prove that you are <span class="short-id">${shortID}</span><br/>
        Sign the following challenge:<br>
        <div id="challenge">${challenge}</div>
        <g:form>
            <g:textField name="response"/><br/>
            <input type="checkbox" name="remember-me" <g:if test="${hasCookie}">checked</g:if> /> Remember me</br>
            <g:actionSubmit value="Submit" action="submitresponse"/>
        </g:form>
        </center>
        <h3>How to sign the challenge</h3>
        <p>First, copy the challenge string to the clipboard.</p>
        <p>With the desktop client, go to "Tools" menu and open the "Sign Tool".  Paste the challenge in the top text area and click "Sign".
        Then click on "Copy To Clipboard" and paste the response in the field above.</p>
        <p>With the plugin, the sign tool is located in the "About Me" page where you copied your Full ID from.  Paste the challenge in the text area
        and click "Sign".  The response will appear in a blue box.  Copy that and paste it in the field above.</p>
        
    </body>
</html>
