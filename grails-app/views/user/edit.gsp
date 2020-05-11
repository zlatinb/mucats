<html>
    <head>
        <title>Edit your profile</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
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
            <hr/>
            <h3>Delete user</h3>
            <g:form>
                <g:hiddenField name="id" value="${user.id}" />
                <input type="checkbox" name="sure"/> Are you sure<br/>
                <g:actionSubmit value="Delete" action="delete"/>
            </g:form>
            <sec:access expression="hasRole('ROLE_ADMIN')">
                <h3>Admin</h3>
                <g:if test="${user.accountLocked}">
                    <g:form>
                        <g:hiddenField name="id" value="${user.id}" />
                        <g:hiddenField name="toLock" value="false" />
                        <g:actionSubmit value="Unlock" action="lock" />
                    </g:form>
                </g:if>
                <g:else>
                    <g:form>
                        <g:hiddenField name="id" value="${user.id}" />
                        <g:hiddenField name="toLock" value="true" />
                        <g:actionSubmit value="Lock" action="lock" />
                    </g:form>
                </g:else>
            </sec:access>
        </g:else>
    </body>
</html>
