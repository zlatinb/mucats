	<html>
    <head>
        <title>Publication</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${flash.message}">
            <div class='errors'>${flash.message}</div>
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
            	<sec:access expression="principal.isAccountNonLocked()">
    	            <g:form action="comment">
        	            <g:hiddenField name="pubId" value="${publication.id}"/>
            	        <g:textArea name="commentText"/>
                	    <g:submitButton name="Comment"/>
                	</g:form>
                	<hr/>
                	<g:if test="${canDelete}">
                		<g:form action="delete">
                			<g:hiddenField name="pubId" value="${publication.id}"/>
                			<g:submitButton name="Delete Publication"/>
                		</g:form>
                	</g:if>
                </sec:access>
            </sec:ifLoggedIn>
        </g:else>
    </body>
</html>
