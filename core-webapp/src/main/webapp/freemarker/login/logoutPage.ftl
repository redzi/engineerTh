<html>
<title>play on ftl</title>
<link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
<#import "/spring.ftl" as spring />
<head>
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>
<#import "../common/logoutPanel.ftl" as logoutPanel>
<#import "../common/indexPanel.ftl" as indexPanel>
<#import "../common/displayUsers.ftl" as displayUsers>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

    <div class="all">
    <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
        <br />Error Message: ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    <#else>
    <#--<@security.authorize access="hasRole('ROLE_USER')">-->
    <#--user-->
    <#--</@security.authorize>-->
        <header>
            <@header.header/>
        </header>
        <nav class="index">
            <@indexPanel.indexPanel/>
        </nav>
        <section>
            <@logoutPanel.logoutPanel/>
        </section>
        <footer>
            <@footer.footer/>
        </footer>
    </#if>

    </div>
</head>
</html>