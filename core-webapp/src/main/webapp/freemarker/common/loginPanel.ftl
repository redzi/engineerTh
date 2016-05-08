<#macro loginPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>

<div class="container">
    <#if Session.SPRING_SECURITY_LAST_EXCEPTION?? && Session.SPRING_SECURITY_LAST_EXCEPTION.message?has_content>
        <div class="alert alert-warning col-sm-offset-2 col-sm-4">
            <br /><strong>Error Message:</strong> ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
        <div class="clearfix"></div>
    </#if>
    <#if failure??>
        <div class="alert alert-warning col-sm-offset-2 col-sm-4">
            <br /><strong>Error Message:</strong> ${failure}<br/>
        </div>
        <div class="clearfix"></div>
    </#if>
    <form class="form-horizontal" role="form" action="" method="post">
        <div id="warning" class="alert alert-warning col-sm-offset-2 col-sm-4">
            <div id="message"></div>
        </div>
        <div class="clearfix"></div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">Username:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" required="required" id="username" name="username" placeholder="Enter username"
                       onblur="checkUser()">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Password:</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" required="required" id="password" name="password" placeholder="Enter password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <div class="checkbox">
                    <label><input type="checkbox" name="remember-me" value="true"> Remember me</label>
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
        var warningSign =  $('#warning');
        warningSign.hide();
        var userName = $('#username');

        var checkUser = function(){
            var path = "users/" + userName.val();
            var request = $.ajax({
                url: path,
                method: "GET",
            });

            request.done(function( response ) {
                warningSign.hide();
            });

            request.fail(function( response ) {
                var message = $('#message');
                message.html("user: " + userName.val() + " does not exist!")
                warningSign.show();
            });
        }
</script>

</#macro>