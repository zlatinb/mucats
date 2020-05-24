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
        <h3>Editing ${publication.name}</h3>
        <g:form action="saveEdited" useToken="true">
        	<g:hiddenField name="pubId" value="${publication.id}"/>
        	
        	<sec:ifAllGranted roles="ROLE_MODERATOR">
        		<label for="featured">Featured</label>
        		<input type="checkbox" name="featured" <g:if test="${publication.featured}">checked</g:if> /><br/>
        	</sec:ifAllGranted>
        	
            <label for="description">Description</label>
            <g:textArea name="description">${publication?.description}</g:textArea><br/>
            <g:submitButton name="Save"/>
        </g:form>
        <g:if test="${publication.image}">
	    	<div class="mcimage">
	    		<img src="<g:createLink controller="publish" action="image" id="${publication.image.id}"/>" width="400"/>
        	</div>
        	<g:form action="deleteImage">
        		<g:hiddenField name="pubId" value="${publication.id}" />
        		<g:submitButton name="Delete"/>
        	</g:form>
	    </g:if>
	    <g:else>
	        <p>Upload image</p>
			<g:uploadForm controller="publish" action="uploadImage" useToken="true">
				<g:hiddenField name="id" value="${publication.id}" />
				<input type="file" name="imageFile" />
				<input type="submit" value="Upload" />
			</g:uploadForm>
		</g:else>
        </center>
    </body>
</html>
