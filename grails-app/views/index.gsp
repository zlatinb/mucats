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

        <p>
            Congratulations, you got MuCats working!  Below you can find a list of all controllers for your convenience.
        </p>
        <div id="controllers" role="navigation">
            <br/>
            <ul>
                <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                    <li class="controller">
                        <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                    </li>
                </g:each>
            </ul>
        </div>
    </section>
</div>

</body>
</html>
