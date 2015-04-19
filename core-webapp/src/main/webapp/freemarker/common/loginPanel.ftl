<#macro loginPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<div class="loginForm">
    <form class="userform" action="/login" method="post">
        <div class="textHolder">
            <table class="formHolder">
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        Password:
                    </td>
                    <td>
                        <input type="password" name="password">
                    </td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="remember-me"/></td><td>Remember me on this computer.</td>
                </tr>
                <tr>
                    <td>
                        <input value="Submit" type="submit">
                    </td>
                    <td>
                        <input value="Reset" type="reset">
                    </td>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </tr>
            </table>
        </div>
    </form>
</div>
</#macro>