<html>
    <head>
        <title>Create Publication</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${flash.error}">
            <div class='errors'>${flash.error}</div>
        </g:if>
        <g:hasErrors bean="${publication}">
            <ul class="errors" role="alert">
                <g:eachError bean="${publication}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>${error}</li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <center>
        <g:form action="save" useToken="true">
            <label for="hash">Hash</label>
            <g:textField name="hash" required value="${publication?.hash}"/><br/>
            <label for="name">Name</label>
            <g:textField name="name" required value="${publication?.name}"/><br/>
            <label for="description">Description</label>
            <g:textArea name="description">${publication?.description}</g:textArea><br/>
            <g:submitButton name="Publish"/>
        </g:form>
        </center>
        
        <div class="message">
        	<div><h3>How to publish files</h3></div>
        	<div>The file you want to publish needs to be shared with MuWire.</div>
        	<div>If using the desktop client, go to the "Uploads" tab, find the file you want to publish, then right-click on it and select "Copy hash to clipboard".
        		Paste the file hash in the "Hash" field above.</div>
        	<div>If using the plugin, go to the "Shared Files" page, find the file you want to publish, then under the "Actions" menu select "Copy hash to clipboard".
        		Paste the file hash in the "Hash" field above.</div>
        	<div>Fill out the "Name" and "Description" fields, and press the "Publish" button.</div>
        </div>
    </body>
</html>
