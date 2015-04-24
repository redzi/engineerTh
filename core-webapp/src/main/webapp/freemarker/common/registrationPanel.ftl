<#macro registrationPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<#assign xhtmlCompliant = true in spring>
<div class="newUser">
        <@spring.bind "user" />
        <form action="" class="registrationForm" method="POST">
            <dl>
                <dd><label for = "name">Name:</label>
                <dd><@spring.formInput  "user.name" />
                <dd><@spring.showErrors "<br>" />
                <dd><label for = "email">Email:</label>
                <dd><@spring.formInput "user.email" />
                <dd><@spring.showErrors "<br>" />
                <dd><label for = "password">Password:</label>
                <dd><@spring.formPasswordInput "user.password" />
                <dd><@spring.showErrors "<br>" />
                <dd><input type="submit" value="Create" />
                <dd></dd><input value="Reset" type="reset">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </dl>
        </form>
</div>

</#macro>