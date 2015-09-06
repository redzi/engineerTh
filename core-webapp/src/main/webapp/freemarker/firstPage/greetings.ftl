<html>
<title>play on ftl</title>
<link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>
<head>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>

    <div class="all">
        <header>
            <@header.header/>
        </header>
        <nav class="index">
            <div class="list-group">
                <a href="/data/currencies" class="list-group-item active">Currency exchange rates</a>
                <a href="/data/stock" class="list-group-item">Stock market data</a>
            </div>
        </nav>
        <aside class="credentials">
            <@security.authorize access="isAnonymous()">
                <a href="/authentication/login" class="btn btn-default btn-sm" role="button">Log in</a>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a href="authentication/logout" class="btn btn-default btn-sm" role="button">log out</a>
            </@security.authorize>
        </aside>

        <section>
            test
        </section>

        <footer>
            <@footer.footer/>
        </footer>

    </div>
</head>
</html>