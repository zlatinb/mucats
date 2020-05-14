<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to MuCats</title>
</head>
<body>

<g:render template="navbar"/>

<div id="content" role="main">
    <center>
        <h1>Welcome to MuCats <sec:ifLoggedIn><sec:username/></sec:ifLoggedIn></h1>
    
    This is an example deployment of the MuCats site code.  You can find the source code
    at <a href="http://git.idk.i2p/zlatinb/mucats">GitLab</a>.  <br/>
    The terms of service of this site are very simple:<br/> 
    <div class="TermsOfService">
    1. No NSFW content<br/>
    2. No copyrighted content unless you own the copyright.<br/>
    </div>  
    Violation to these rules will result in your account getting locked.<br/><br/>
    Enjoy!
    
    

    </center>
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
