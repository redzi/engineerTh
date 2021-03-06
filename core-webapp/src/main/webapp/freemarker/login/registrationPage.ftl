<html>
<title>play on ftl</title>
<link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
<#import "/spring.ftl" as spring />
<head>
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>
<#import "../common/loginPanel.ftl" as loginPanel>
<#import "../common/indexPanel.ftl" as indexPanel>
<#import "../common/registrationPanel.ftl" as registrationPanel>
<#import "../common/displayUsers.ftl" as displayUsers>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

    <div class="all">
        <header>
        <@header.header/>
        </header>
        <nav class="index">
        <@indexPanel.indexPanel/>
        </nav>
        <section>
            <div class="error">
            <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
                <br />Error Message: ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
            </#if>
            </div>
        <@registrationPanel.registrationPanel/>
        </section>
        <footer>
        <@footer.footer/>
        </footer>
    </div>
</head>
</html>