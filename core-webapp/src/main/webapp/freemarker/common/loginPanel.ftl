<#macro loginPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<div class="loginForm">
    <form class="userform" name="newuser" action="/list" method="post">
        <div class="textHolder">
            <table class="formHolder">
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input type="text" name="word">
                    </td>
                </tr>
                <tr>
                    <td>
                        E-mail:
                    </td>
                    <td>
                        <input type="text" name="email">
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
                    <td>
                        <input value="Save" type="submit">
                    </td>
                    <td>
                        <input value="Reset" type="reset">
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</#macro>