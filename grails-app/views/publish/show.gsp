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
        	<div class="description">
            	<h3>${publication.name}</h3>
            	<div>
            	<div class="mytooltip">Hash: <span class="hash">${publication.hash}</span><span class="mytooltiptext">Search for this hash with MuWire</span></div>
            	</div>
            	<g:if test="${publication.description}">
	            	<pre class="description">${publication.description}</pre>
	            	<g:if test="${publication.lastEdited}">
	            		<p>Last edited: ${publication.lastEdited}</p>
	            	</g:if>
	            </g:if>
	            <g:else>
	            	<div>No description provided</div>
	            </g:else>
            	<g:if test="${canDelete}">
            		<p><g:link action="edit" id="${publication.id}">Edit Description</g:link></p>
            	</g:if>
            </div>
            <h3>Comments:</h3>
                <g:each var="comment" in="${publication.comments}">
                    <div class="comment">
                        <g:link controller="user" action="show" id="${comment.user.id}">${comment.user.username}</g:link>  ${comment.date}<br/>
                        <pre class="comment">${comment.comment}</pre>
                    </div>
                </g:each>
            <hr/>
            <sec:ifLoggedIn>
            	<sec:access expression="principal.isAccountNonLocked()">
    	            <g:form action="comment" useToken="true">
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
