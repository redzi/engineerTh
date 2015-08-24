<html>
<title>Currency exchange data</title>
    <head>
        <link type="text/css" href="/resources/greetPage.css" rel="stylesheet" />
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "../common/header.ftl" as header>
<#import "../common/footer.ftl" as footer>
    </head>
    <body>
    <div class="all">
        <header>
        <@header.header/>
        </header>
        <nav class="index">
            <div class="list-group">
                <a href="/data/currencies" class="list-group-item active">Currency exchange rates</a>
                <a href="#" class="list-group-item">Stock market data</a>
            </div>
        </nav>
        <aside class="credentials">
        <@security.authorize access="isAnonymous()">
            <a href="/authentication/login" class="btn btn-default btn-sm" role="button">Log in</a>
        </@security.authorize>
        <@security.authorize access="isAuthenticated()">
            <a href="/authentication/logout" class="btn btn-default btn-sm" role="button">log out</a>
        </@security.authorize>
        </aside>

        <section>
        <#if availableCurrenciesMap??>
            <#assign keys = availableCurrenciesMap?keys>
            <div class="currencyExchange">
                Which currency would you like to see?<br/>
                <div class="dropdown">
                    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Currencies
                        <span class="caret"></span></button>
                    <ul id="currencyList" class="dropdown-menu scrollable-menu" role="menu">
                        <#list keys as key>
                            <li><a href="">${key} - ${availableCurrenciesMap[key]}</a></li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>
            <div id="linechart_material"></div>
            <div id="currencyTableHolder">
                <table id="currencyTable"></table>
            </div>
        </section>

        <footer>
        <@footer.footer/>
        </footer>

    </div>

    </body>

<script>
    $( document ).ready(function() {
        var base = 'cur';
        $('ul li').each(function(i){
            $(this).attr('id', base+'_'+i);
            $(this).click(getRates);
        });
        $('#currencyList').click(showCurrencyList);

        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
    });


    var baseCurrencyTo = "";

    var getRates = function(event){
        clearCurrencyTable();
        var targetNode = event.target;
        var currency = $(targetNode).text().substring(0,3);
        baseCurrencyTo = currency;
        var path = "/data/currencies/" + currency;
        var request = $.ajax({
            url: path,
            method: "GET",
        });
        //hideCurrencyList();

        request.done(function( response ) {
            var table = $('#currencyTable');
            var rates = JSON.parse(response);
            var data = rates.rates;
            if(data){
                var flag = false;
                var txt = "";
                $.each(data, function(key, value){
                    flag = true;
                    var round = parseFloat(value).toFixed(2);
                    $(table).append("<tr><td>"+key+"</td><td>"+round+"</td></tr>");
                });
                if(flag){
                    $(table).show();
                    $(table).on("click", "td", getHistoricalRates);
                }
            }
        });
        return false;
    }

    var getHistoricalRates = function(event){
        var currency = $(this).parent().children().first().text();
        var path = '/data/currencies/history';

        var request = $.post(path, { baseCurrency: baseCurrencyTo, compareToCurrency: currency });

        request.done(function( response ) {
            var table = $('#currencyGraph');
            var rates = JSON.parse(response);

            var feedData = [];

            for(var i = 0; i < getLength(rates); i++ )
            {
                $.each(rates[i], function(key, value){
                    feedData[i] = new Array();
                    feedData[i][0] = new Date(key);
                    feedData[i][1] = Math.round(value * 100) / 100;
                });
            }


            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Date');
            data.addColumn('number', baseCurrencyTo + ' to '+ currency);


            data.addRows(feedData);


            var options = {
                title: 'Exchange rate: ' + baseCurrencyTo + ' to '+ currency,
                width: 700,
                height: 400,
                hAxis: {
                    format: 'd/M/yy',
                    gridlines: {count: 15}
                },
                vAxis: {
                    gridlines: {color: 'none'},
                }
            };

            var chart = new google.visualization.LineChart(document.getElementById('linechart_material'));

            chart.draw(data, options);


        });
    }

    var hideCurrencyList = function(){
        $('#currencyList').hide();
    };

    var showCurrencyList = function(){
        alert("wykonalo sie");
        $('#currencyList').show();
    };

    var clearCurrencyTable = function(){
        $('#currencyTable').find("tr:gt(0)").remove();
    }

    var getLength = function(obj){
        var len = 0;
        $.each(obj, function(i, elem) {
            len++;
        });
        return len;
    }

    google.load('visualization', '1', {packages: ['corechart']});


</script>

</html>

