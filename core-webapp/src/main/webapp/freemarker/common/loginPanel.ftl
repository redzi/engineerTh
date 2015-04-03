<#macro loginPanel>
<link type="text/css" href="/resources/main.css" rel="stylesheet" />
<div class="loginForm">
    <form class="userform" name="newuser" action="login" method="post">
        <div class="textHolder">
            Firstname: <input type="text" name="firstname"> <br>
            Lastname: <input type="text" name="lastname"> <br>
            E-mail: <input type="text" name="email"> <br>
            Password: <input type="password" name="password"> <br>
        </div>
        <div class="buttonHolder">
            <input value="Save" type="submit">
            <input value="Reset" type="reset">
        </div>
    </form>
</div>
</#macro>