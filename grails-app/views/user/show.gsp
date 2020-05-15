<html>
    <head>
        <title>User Profile</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
        	<center>
	            <h3 class="profile">${user.username}</h3>
	            <g:if test="${user.accountLocked}">
	                <div class="errors">Account Locked!</div>
	            </g:if>
		        <g:if test="${user.showFullId}">
	            	<div class="fullId">
		                Full ID:<br/>
		                <textarea readonly>${user.personaB64}</textarea>
	            	</div>
		        </g:if>
	            <div class="profile">
		            Public profile	            
		            <g:if test="${canEdit}">
		                (<g:link action="edit" id="${user.id}">Edit</g:link>)
		            </g:if>
		            :<br/>
		            <pre class="description">${profile}</pre>
	            </div>
            </center>
        </g:else>
    </body>
</html>
