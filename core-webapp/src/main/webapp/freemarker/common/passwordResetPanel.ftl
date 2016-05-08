<#macro passwordResetPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
    <#import "/spring.ftl" as spring />
    <#assign xhtmlCompliant = true in spring>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-route.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-resource.js"></script>

<div ng-app="resetPassword" ng-controller="resetPasswordCtrl" class="container">
    <#if failure??>
        <div class="alert alert-warning col-sm-4">
            <br /> ${failure}<br/>
        </div>
    </#if>
    <form class="form-horizontal" role="form" action="" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Username:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="username" name="username" placeholder="Enter username">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">Email:</label>
            <div class="col-sm-4">
                <input type="email" class="form-control" id="emailAddress" placeholder="Enter email" name="emailAddress">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button type="submit" class="btn btn-default">Change</button>
                <button type="reset" class="btn btn-default">Reset</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <a href="/" class="btn btn-default btn-sm" role="button">Back to main page</a>
            </div>
        </div>
    </form>
</div>
</#macro>