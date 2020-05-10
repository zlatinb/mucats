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
            <g:form>
                <g:hiddenField name="id" value="${user.id}" />
                <g:hiddenField name="version" value="${user.version}"/>
                <input type="checkbox" name="showFullId" <g:if test="${user.showFullId}">checked</g:if> />Show Full ID<br/>
                <g:textArea name="profile">${user.profile}</g:textArea>
                <g:actionSubmit value="Save" action="update"/>
            </g:form>
        </g:else>
    </body>
</html>
