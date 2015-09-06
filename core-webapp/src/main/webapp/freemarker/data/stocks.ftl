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
    <@security.authorize access="isAnonymous()">
        <a href="/authentication/login" class="btn btn-default btn-sm" role="button">Log in</a>
    </@security.authorize>
    <@security.authorize access="isAuthenticated()">
        <a href="/authentication/logout" class="btn btn-default btn-sm" role="button">log out</a>
    </@security.authorize>
    </aside>

    <section>
    <#if availableStocksMap??>
        <#assign keys = availableStocksMap?keys>
        <div id="myTableHolder">
            <table id="myTable" class="table table-striped" >
                <thead>
                <tr>
                    <th>Symbol</th>
                    <th>Company</th>
                </tr>
                </thead>
                <tbody>
                    <#list keys as key>
                    <tr>
                        <td class="code">${key}</td>
                        <td>${availableStocksMap[key]}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </#if>
        <div id="stockDataHolder" style="width:50%; height: 200px; background-color: grey;">
            <div id="stockDataGraph" style="width: 20%; float:left; margin: 1%; background-color: grey;">A</div>
            <div id="stockDataQuotesInfo" style="width: 20%; float:left; margin: 1%; background-color: grey;">B</div>
        </div>
    </section>

    <footer>
    <@footer.footer/>
    </footer>

</div>

</body>

<script>
    $( document ).ready(function() {
        hideMyTable();
        $('#myTable').dataTable();

        var base = 'stock';
        $('#myTable tr').each(function(i){
            $(this).attr('id', base+'_'+i);
            $(this).click(getQuota);
        });

        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
        showMyTable();
    });

    var getQuota = function(event){
        clearMyTable();
        var targetNode = event.target;
        var codeNode = $(targetNode).parent().children('td.code');
        var code = $(codeNode).text();
        var path = "/data/stock/" + code;
        var request = $.ajax({
            url: path,
            method: "GET",
        });
        hideMyTable();

        request.done(function( response ) {
            var data = JSON.parse(response);
            showQuoteData(data);

        });
        return false;
    }

    var showQuoteData = function(data){
        var baseDataset = data.dataset;
        var change = data.change;
        var daily = JSON.parse(change.daily);
        var weekly = JSON.parse(change.weekly);
        var monthly = JSON.parse(change.monthly);
        var annual = JSON.parse(change.annual);
        var currentData = baseDataset.data;

        var dailyQuotes = daily.dataset;
        var dailyDate = dailyQuotes.end_date;
        dailyQuotes = dailyQuotes.data;
        dailyQuotes = dailyQuotes[0];

        var weeklyQuotes = weekly.dataset;
        var weeklyDate = weekly.end_date;
        weeklyQuotes = weeklyQuotes.data;
        weeklyQuotes = weeklyQuotes[0];

        var monthlyQuotes = monthly.dataset;
        var monthlyDate = monthlyQuotes.end_date;
        monthlyQuotes = monthlyQuotes.data;
        monthlyQuotes = monthlyQuotes[0];

        var annualQuotes = annual.dataset;
        var annualDate = annualQuotes.end_date;
        annualQuotes = annualQuotes.data;
        annualQuotes = annualQuotes[0];

        var currentLastDate = baseDataset.end_date;

        var quotes = currentData[0];
        var currentOpenQuota = quotes[1]
        var currentHighQuota = quotes[2];
        var currentLowQuota = quotes[3];
        var currentCloseQuota = quotes[4];

        var dailyCloseQuota = dailyQuotes[4];
        var weeklyCloseQuota = weeklyQuotes[4];
        var monthlyCloseQuota = monthlyQuotes[4];
        var annualCloseQuota = annualQuotes[4];

        var dailyPercentChange = calculatePercentChange(currentCloseQuota, dailyCloseQuota);
        var weeklyPercentChange = calculatePercentChange(currentCloseQuota, weeklyCloseQuota);
        var monthlyPercentChange = calculatePercentChange(currentCloseQuota, monthlyCloseQuota);
        var annualPercentChange = calculatePercentChange(currentCloseQuota, annualCloseQuota);

        var table = $('#stockDataTable');

        $(table).append("<tr><td class='graph' c>"+lastDate+"</td><td><table class='dataView'><tr><td>test</td></tr></table></td></tr>");

        $('#dataView').append("<tr><td class='graph'>"+lastDate+"</td><td class='dataView'></td></tr>")




        if(lastQuota){
            var txt = "";
            var graph = $('stockDataGraph');
        }


    }

    var getHistoricalRates = function(event){
        var currency = $(this).parent().children().first().text();
        var path = '/data/stock/history';

        var request = $.post(path, { stock: baseCurrencyTo, startDate: start, endDate: end});

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

    var calculatePercentChange = function(basePrice, price){
        var diffPercent = ((basePrice / price) * 100 - 100).toFixed(2);
        return diffPercent;
    }

    var clearMyTable = function(){
        $('#myTable').find("tr:gt(0)").remove();
    }

    var hideMyTable = function(){
        $('#myTableHolder').hide();
    }

    var showMyTable = function(){
        $("#myTableHolder").show();
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

