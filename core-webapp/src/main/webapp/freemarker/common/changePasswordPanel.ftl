<#macro changePasswordPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<#assign xhtmlCompliant = true in spring>
<div class="changePassword">
    ${message}<br>
    <form action="" class="changePasswordForm" method="POST">
        <dl>
            <dd><label for = "password">Current password:</label>
            <dd><input type="password" name="password" />
            <dd><label for = "password">Password:</label>
            <dd><input type="password" name="changedPassword" />
            <dd><label for = "password">Password confirmation:</label>
            <dd><input type="password" name="passwordConfirmation" />
            <dd><input type="submit" value="Change" />
            <dd></dd><input value="Reset" type="Reset">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </dl>
    </form>
</div>
</#macro>