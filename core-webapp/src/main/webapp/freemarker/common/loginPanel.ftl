<#macro loginPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src= "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<div ng-app="login" ng-controller="loginCtrl" class="container">
    <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
        <div class="alert alert-warning">
            <br /><strong>Error Message:</strong> ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if failure??>
        <div class="alert alert-warning">
            <br /><strong>Error Message:</strong> ${failure}<br/>
        </div>
    </#if>
    <form class="form-horizontal" role="form" action="" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">Username:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" ng-model="username"
                       ng-blur="checkUser()">
            </div>
        </div>
        <div class="alert alert-danger" ng-show="shown"> {{message}} </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Password:</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <div class="checkbox">
                    <label><input type="checkbox"> Remember me</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button type="submit" class="btn btn-default">Submit</button>
                <button type="reset" class="btn btn-default">Reset</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <a href="/authentication/registration" class="btn btn-default btn-sm" role="button">Registration</a>
                <a href="/authentication/passwordReset" class="btn btn-default btn-sm" role="button">Forgot my password</a>
            </div>
        </div>
    </form>
</div>
<script>
    var app = angular.module('login', []);
    app.controller('loginCtrl', function($scope, $http) {
        $scope.shown = false;

        $scope.checkUser = function() {
            var path = "/users/" + $scope.username;
            $scope.shown = true;
            var responsePromise = $http.get(path);
            responsePromise.success(function (responce) {
                $scope.shown = true;
                $scope.message = "ok";
                var dataprop = responce;
            });
            responsePromise.error(function (data, status, headers, config) {
                $scope.shown = true;
                $scope.message = "failed";
            });
        };
    });
</script>
</#macro>