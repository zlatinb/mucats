<html>
    <head>
        <title>User Profile</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
            <h3>${user.username}</h3>
            <g:if test="${user.accountLocked}">
                <p>Account Locked!</p>
            </g:if>
            <g:if test="${canEdit}">
                <g:link action="edit" id="${user.id}">Edit Profile</g:link>
            </g:if>
            <hr/>
            <g:if test="${user.showFullId}">
                <p>Full ID:</p>
                <textarea readonly>${user.personaB64}</textarea>
            </g:if>
            <g:else>
                <p>This user has chosen not to show their Full ID</p>
            </g:else>
            <hr/>
            <p>Public profile:</p>
            <pre>${profile}</pre>
        </g:else>
    </body>
</html>
