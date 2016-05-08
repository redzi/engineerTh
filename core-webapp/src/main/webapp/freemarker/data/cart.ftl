<html>
<title>Stock market data</title>
<head>
    <link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet"
          href="https://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css" />
    <script type="text/javascript"
            src="https://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript"
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
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
    <#if cartData??>
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
                <#if cartData.products??>
                    <#list cartData.products as key>
                        <tr>
                            <td>
                            ${inc}
                            </td>
                            <td>
                            ${key.stockName.code}
                            </td>
                            <td>
                            <#assign price = key.pricePerUnit>
                            ${key.pricePerUnit}
                            </td>
                            <td>
                            <#assign no = key.unitNo>
                            ${key.unitNo}
                            </td>
                            <td>
                           ${price * no}
                            </td>
                            <td>
                                <a href="#" class="btn btn-default btn-sm" role="button" id="${inc}"
                                   onclick="removeFromCart(this)">Remove from cart</a>
                            </td>
                            <#assign inc = inc + 1>
                        </tr>
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
                                Total:
                            </td>
                            <td>
                            ${totalPrice}
                            </td>
                            <td>
                            </td>
                        </tr>
                    </#if>
                </#if>
            </table>
        </div>
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

    var removeFromCart = function(element) {

        var path = '/data/product/cart/' + element.getAttribute('id');

        $.post( path, { id: element.getAttribute('id') })
                .done(function( response ) {
                    location.reload();
                });
    }

</script>