<html>
    <head>
        <title>Publication</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
            <h3>${publication.name}</h3>
            <p>Hash: ${publication.hash}</p>
            <p>Description:</p>
            <pre>${publication.description}</p>
            <hr/>
            <h3>Comments:</h3>
                <g:each var="comment" in="${publication.comments}">
                    <div class="comment">
                        <g:link controller="user" action="show" id="${comment.user.id}">${comment.user.username}</g:link>  ${comment.date}<br/>
                        <pre>${comment.comment}</pre>
                    </div>
                </g:each>
            <hr/>
            <sec:ifLoggedIn>
                <g:form action="comment">
                    <g:hiddenField name="pubId" value="${publication.id}"/>
                    <g:textArea name="commentText"/>
                    <g:submitButton name="Comment"/>
                </g:form>
            </sec:ifLoggedIn>
        </g:else>
    </body>
</html>
