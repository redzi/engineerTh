<#macro registrationPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<#assign xhtmlCompliant = true in spring>
<#import "../common/inputs.ftl" as inputs>
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
        <div class="alert alert-warning col-sm-offset-2 col-sm-4" ng-cloak>
            <br /><strong>Error Message:</strong> ${failure}<br/>
        </div>
        <div class="clearfix"></div>
    </#if>
    <div class="clearfix"></div>
    <@spring.bind "user" />
    <form class="form-horizontal" role="form" action="" method="post">
        <div id="warning" class="alert alert-warning col-sm-offset-2 col-sm-4">
            <div id="message"></div>
        </div>
        <div class="clearfix"></div>
        <@inputs.textInputWithMessage "user.name" "class='form-control' placeholder='Enter username' id='username'
                       onblur='checkUser()'" "Username:" "text"/>
        <@spring.showErrors "<br>" "class='alert alert-warning col-sm-offset-2 col-sm-4'" />
        <div class="clearfix"></div>
        <@inputs.textInputWithMessage "user.email" "class='form-control' placeholder='Enter email'" "Email:" "email"/>
        <@spring.showErrors "<br>" "class='alert alert-warning col-sm-offset-2 col-sm-4'" />
        <div class="clearfix"></div>
        <@inputs.textInputWithMessage "user.password" "class='form-control' placeholder='Enter password'" "Password:" "password"/>
        <@spring.showErrors "<br>" "class='alert alert-warning col-sm-offset-2 col-sm-4'" />
        <div class="clearfix"></div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button type="submit" class="btn btn-default">Submit</button>
                <button type="reset" class="btn btn-default">Reset</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </div>
     </form>
</div>

<script>
    var warningSign =  $('#warning');
    warningSign.hide();
    var userName = $('#name');

    var checkUser = function(){
        var path = "users/" + userName.val();
        var request = $.ajax({
            url: path,
            method: "GET",
        });
        request.done(function( response ) {
            var message = $('#message');
            message.html("user: " + userName.val() + " already exists!");
            warningSign.show();

        });
        request.fail(function( response ) {
            warningSign.hide();
        });
    }
</script>
</#macro>

