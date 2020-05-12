<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to MuCats</title>
</head>
<body>

<g:render template="navbar"/>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Welcome to MuCats <sec:ifLoggedIn><sec:username/></sec:ifLoggedIn></h1>

    </section>
    <g:if test="${flash.error}">
    	<div class="errors">${flash.error}</div>
   	</g:if>
        <div>
        	<p>Recent Publications</p>
        	<g:include controller="publish" action="list" />
        </div>
        
</div>

</body>
</html>
