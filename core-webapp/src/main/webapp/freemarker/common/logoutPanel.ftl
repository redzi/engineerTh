<#macro logoutPanel>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<link type="text/css" href="/resources/main.css" rel="stylesheet" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-route.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-resource.js"></script>

<div ng-app="logout" ng-controller="logoutCtrl" class="container">
    <div class="alert alert-info">
        ${message}
    </div>
    <form class="form-horizontal" role="form" action="/j_spring_security_logout" method="post">
        <div class="form-group">
            <div class="col-sm-offset-0 col-sm-4">
                <button type="submit" class="btn btn-default">Log out</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-0 col-sm-4">
                <a href="/" class="btn btn-default btn-sm" role="button">Go back to main page</a>
            </div>
        </div>
    </form>
</div>
</#macro>