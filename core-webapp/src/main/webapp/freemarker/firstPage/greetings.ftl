<html>
<title>play on ftl</title>
<link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
<head>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>

    <div class="all">
        <header>
            <@header.header/>
        </header>
        <@security.authorize access="hasRole('ROLE_USER')">
        ${user}
        </@security.authorize>

        <nav class="index">
            1<br>
            2<br>
            3<br>
        </nav>
        <nav class="login">
            Log in<br>
        </nav>
        <section>
            <h2>test</h2>
        </section>

        <footer>
            <@footer.footer/>
        </footer>

    </div>
</head>
</html>