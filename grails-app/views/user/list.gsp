<html>
    <head>
        <title>User List</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:render contextPath="/" template="navbar"/>
        <g:if test="${error}">
            <div class='errors'>${error}</div>
        </g:if>
        <g:else>
            <h3>Users</h3>
            <table>
                <thead>
                    <tr>
                        <th>User</t>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${users}" var="user">
                        <tr>
                            <td><g:link action="show" id="${user.id}">${user.username}</g:link></td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </g:else>
    </body>
</html>
