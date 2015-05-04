<#macro registrationPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<#assign xhtmlCompliant = true in spring>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-route.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-resource.js"></script>

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
<#--<script>-->
<#--var app = angular.module('registration', []);-->
<#--app.controller('userExists', function($scope, $http) {-->
    <#--$http.get("http://www.w3schools.com/angular/customers.php")-->
            <#--.success(function(response) {$scope.names = response.records;});-->
<#--});-->
<#--</script>-->
</#macro>