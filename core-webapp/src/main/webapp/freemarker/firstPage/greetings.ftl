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
        <nav class="index">
            1<br>
            2<br>
            3<br>
        </nav>
        <aside class="credentials">
            <@security.authorize access="isAnonymous()">
                <a href="/authentication/login">Log in</a>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a href="/authentication/logout">Log out</a>
            </@security.authorize>
        </aside>

        <section>
            <h2>test</h2>
        </section>

        <footer>
            <@footer.footer/>
        </footer>

    </div>
</head>
</html>