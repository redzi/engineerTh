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
<#import "../common/changePasswordPanel.ftl" as changePasswordPanel>
<#import "../common/passwordResetPanel.ftl" as passwordResetPanel>
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
        <@passwordResetPanel.passwordResetPanel/>
        </section>
        <footer>
        <@footer.footer/>
        </footer>
    </div>
</head>
</html>