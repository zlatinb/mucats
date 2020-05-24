	<!doctype html>
	<html>
	<head>
	    <meta name="layout" content="main"/>
	    <title>Welcome to MuCats</title>
	</head>
	<body>
	
	<g:render contextPath="/" template="navbar"/>

<div id="mylist">

	<div id="mysidebar">
		<p>Featured Publications</p>
		<g:each in="${featured}" var="publication">
			<div class="feature">
				<g:if test="${publication.image}">
					<img src="<g:createLink controller="publish" action="image" id="${publication.image.id}"/>" width="140"/>
				</g:if>
				<p><g:link action="show" id="${publication.id}">${publication.name}</g:link></p>
			</div>
		</g:each>
	</div>

	<div id="myresults">
		<g:if test="${q}">
			<p>Results for ${q}</p>
		</g:if>
		<g:if test="${total > 0}">
		<p>Total Publications ${total}</p>
		    <table>
		        <thead>
		            <tr>
		                <th>Name</th>
		                <th>Hash</th>
		                <th>Publisher</th>
		                <th>Date</th>
		            </tr>
		        </thead>
		        <tbody>
		            <g:each in="${publications}" var="publication">
		                <tr>
		                    <td><g:link action="show" id="${publication.id}">${publication.name}</g:link></td>
		                    <td>${publication.hash}</td>
		                    <td><g:link controller="user" action="show" id="${publication.user.id}">${publication.user.username}</g:link></td>
		                    <td>${publication.date}</td>
		                </tr>
		            </g:each>
		        </tbody>
		    </table>
		    <div class="pagination">
		        <g:paginate total="${total}" params='[q : "${q}"]'/>
		    </div>            
		</g:if>
		<g:else>
		<p>No publications in the database!</p>
		</g:else>
	</div>
</div>
</body>
</html>