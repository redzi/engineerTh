<html>
<title>Stock market data</title>
<head>
    <link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet"
          href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css" />
    <script type="text/javascript"
            src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript"
            src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>
    <style>

    </style>
</head>
<body>
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
    <@security.authorize access="isAuthenticated()">
        <a href="/authentication/logout" class="btn btn-default btn-sm" role="button">log out</a>
        <a href="/data/product/cart" class="btn btn-default btn-sm" role="button">view cart content</a>
    </@security.authorize>
    </aside>

    <section>
    <@security.authorize access="isAuthenticated()">
    <#if cart??>
        <#if totals??>
        <div id="myTableHolder">
            <#assign inc = 1>
            <table class="displayCartItems">
                <tr>
                    <th>
                        Item no.
                    </th>
                    <th>
                        Stock code
                    </th>
                    <th>
                        Price per unit
                    </th>
                    <th>
                        No of units
                    </th>
                    <th>
                        Total value
                    </th>
                    <th>
                    </th>
                </tr>
                <#list cart as product>
                    <#list totals as total>
                    <tr>
                        <td>
                        ${inc}
                        </td>
                        <#assign inc = inc +1>
                        <td>
                        ${product.stockName.code}
                        </td>
                        <td>
                        ${product.pricePerUnit}
                        </td>
                        <td>
                        ${product.unitNo}
                        </td>
                        <td>
                            ${total}
                        </td>
                        <td>
                            <a href="/data/product/cart" class="btn btn-default btn-sm" role="button">delete</a>
                        </td>
                    </tr>
                    </#list>
                </#list>
                <#if totalPrice??>
                    <tr>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        Total:
                        </td>
                        <td>
                            ${totalPrice}
                        </td>
                    </tr>
                </#if>
            </table>
        </div>
        </#if>
    <#else>
        <div id="warning" class="alert alert-warning col-sm-offset-2 col-sm-4">
            <div id="message">Your cart is empty!</div>
        </div>
    </#if>
    </@security.authorize>

    <@security.authorize access="isAuthenticated()">
        <#--<div id="buyHolder">-->
            <#--<form class="form-horizontal" role="form" action="/data/product/addProduct" method="post">-->
            <#--&lt;#&ndash;<div id="warning" class="alert alert-warning col-sm-offset-2 col-sm-4">&ndash;&gt;-->
            <#--&lt;#&ndash;<div id="message"></div>&ndash;&gt;-->
            <#--&lt;#&ndash;</div>&ndash;&gt;-->
                <#--<div class="clearfix"></div>-->
                <#--<div class="form-group">-->
                    <#--<label class="control-label col-sm-2" for="stockName">Code:</label>-->
                    <#--<div class="col-sm-4" >-->
                        <#--<input type="text" class="form-control" required="required" id="stockName" name="stockName"-->
                               <#--readonly="readonly" value="${productCode}" onblur="">-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<label class="control-label col-sm-2" for="pricePerUnit">Price per stock:</label>-->
                    <#--<div class="col-sm-4">-->
                        <#--<input type="text" class="form-control" required="required" id="pricePerUnit" name="pricePerUnit"-->
                               <#--readonly="readonly" value="${pricePerUnit}" onblur="">-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<label class="control-label col-sm-2" for="unitNo">Number of stock(s):</label>-->
                    <#--<div class="col-sm-4">-->
                        <#--<input type="number" class="form-control" required="required" id="unitNo" name="unitNo" value="1"-->
                               <#--onchange="calculateTotal()">-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<label class="control-label col-sm-2" for="unitNo">Total price:</label>-->
                    <#--<div class="col-sm-4">-->
                        <#--<input type="text" class="form-control" required="required" id="totalPrice" name="totalPrice"-->
                               <#--value="${pricePerUnit}" readonly="readonly" >-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<div class="col-sm-offset-2 col-sm-4">-->
                        <#--<button type="submit" class="btn btn-default">Buy</button>-->
                        <#--<button type="reset" class="btn btn-default">Reset</button>-->
                        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<div class="col-sm-offset-2 col-sm-4">-->
                        <#--<a href="/data/product/" class="btn btn-default btn-sm" role="button">Get back</a>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</form>-->
        <#--</div>-->
    </@security.authorize>

    </section>

    <footer>
    <@footer.footer/>
    </footer>

</div>

</body>

<script>
    $( document ).ready(function() {

        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
    });

</script>