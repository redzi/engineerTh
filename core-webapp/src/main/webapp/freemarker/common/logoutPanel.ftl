<#macro logoutPanel>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<div class="logoutForm">
        <div class="textHolder">
            <table class="formHolder">
                <tr>
                    <td>
                        <h3>${message}</h3>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form class="userform" action="/j_spring_security_logout" method="post">
                            <input type="submit" value="Log out" />
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        Or go back to: <a href="/" >the main page</a>
                    </td>
                </tr>
            </table>
        </div>
</div>
</#macro>