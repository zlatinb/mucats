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
        <g:form action="save">
            <label for="hash">Hash</label>
            <g:textField name="hash" required value="${publication?.hash}"/>
            <label for="name">Name</label>
            <g:textField name="name" required value="${publication?.name}"/>
            <label for="description">Description</label>
            <g:textArea name="description">${publication?.description}</g:textArea>
            <g:submitButton name="Publish"/>
        </g:form>
    </body>
</html>
