	<!doctype html>
	<html>
	<head>
	    <meta name="layout" content="main"/>
	    <title>Welcome to MuCats</title>
	</head>
	<body>
	
	<g:render contextPath="/" template="navbar"/>

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
        <g:paginate total="${total}"/>
    </div>            
</g:if>
<g:else>
<p>No publications in the database!</p>
</g:else>

</body>
</html>