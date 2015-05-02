<#macro passwordResetPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
    <#import "/spring.ftl" as spring />
    <#assign xhtmlCompliant = true in spring>
<div class="resetPassword">
    <form action="" class="passwordResetForm" method="POST">
        <dl>
            <dd><label for = "username">Your username:</label>
            <dd><input type="text" name="username" />
            <dd><label for = "email">Email:</label>
            <dd><input type="text" name="emailAddress" />
            <dd><input type="submit" value="Change" />
            <dd></dd><input value="Reset" type="Reset">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </dl>
    </form>
</div>
</#macro>