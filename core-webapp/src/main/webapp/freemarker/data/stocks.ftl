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
    <@security.authorize access="isAnonymous()">
        <a href="/authentication/login" class="btn btn-default btn-sm" role="button">Log in</a>
    </@security.authorize>
    <@security.authorize access="isAuthenticated()">
        <a href="/authentication/logout" class="btn btn-default btn-sm" role="button">log out</a>
        <a href="/data/product/cart" class="btn btn-default btn-sm" role="button">view cart content</a>
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
        <div id="stockDataHolder" class="dataHolder">
            <div id="linechart_material" >
            </div>
            <div id="stockDataQuotesInfo" >
                <table>
                    <tr>
                        <td>Current value (close value): </td>
                        <td id="val"></td>
                    </tr>
                    <tr>
                        <td id="dailyDate">Daily percentage change to last pricing: </td>
                        <td id="daily"></td>
                    </tr>
                    <tr>
                        <td>Last session opening value: </td>
                        <td id="open"></td>
                    </tr>
                    <tr>
                        <td>Last session highest value: </td>
                        <td id="high"></td>
                    </tr>
                    <tr>
                        <td>Last session lowest value: </td>
                        <td id="low"></td>
                    </tr>
                    <tr>
                        <td id="weeklyDate">Weekly percentage change: </td>
                        <td id="weekly"></td>
                    </tr>
                    <tr>
                        <td id="monthlyDate">Monthly percentage change: </td>
                        <td id="monthly"></td>
                    </tr>
                    <tr>
                        <td id="annualDate">Annual percentage change: </td>
                        <td id="annual"></td>
                    </tr>
                </table>
            </div>
            <div style="clear: both;" > </div>
        </div>

    <@security.authorize access="isAuthenticated()">
        <div id="buyHolder">
        </div>
    </@security.authorize>

    </section>

    <footer>
    <@footer.footer/>
    </footer>

</div>

</body>

<script>
    $( document ).ready(function() {
        hideMyTable($('#myTableHolder'));
        hideMyTable( $('#stockDataQuotesInfo'))
        $('#myTable').dataTable();

        var base = 'stock';
        $(function () {
            $('#myTable tr').each(function(i){
                $(this).attr('id', base+'_'+i);
                $(this).click(getQuota);
            });
            $(document).on('click','#myTable tr',function(){
                getQuota(event);
            });
        });


        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
        showMyTable($('#myTableHolder'));
    });

    var getQuota = function(event){
        clearMyTable($('#myTable'));
        var targetNode = event.target;
        var codeNode = $(targetNode).parent().children('td.code');
        var code = $(codeNode).text();
        var path = "/data/stock/" + code;
        var request = $.ajax({
            url: path,
            method: "GET",
        });
        hideMyTable( $('#myTableHolder'));

        request.done(function( response ) {
            var data = JSON.parse(response);
            showQuoteData(data, code);

        });
        return false;
    }

    var showQuoteData = function(data, stockName){
        var baseDataset = data.dataset;
        var change = data.change;
        var daily = JSON.parse(change.daily);
        var weekly = JSON.parse(change.weekly);
        var monthly = JSON.parse(change.monthly);
        var annual = JSON.parse(change.annual);
        var currentData = baseDataset.data;

        var graphData = data.graph;
        graphData = JSON.parse(graphData);
        graphData = graphData.dataset;

        var dailyQuotes = daily.dataset;
        var dailyDate = dailyQuotes.end_date;
        dailyQuotes = dailyQuotes.data;
        dailyQuotes = dailyQuotes[0];

        var weeklyQuotes = weekly.dataset;
        var weeklyDate = weeklyQuotes.end_date;
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
        var currentOpenQuota = quotes[1];
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

        var quotesContainer = $('#stockDataQuotesInfo');

        var quotesData = new Array();

        quotesData.push(currentData);
        quotesData.push(currentCloseQuota);
        quotesData.push(currentOpenQuota);
        quotesData.push(currentHighQuota);
        quotesData.push(currentLowQuota);
        quotesData.push(dailyDate);
        quotesData.push(dailyPercentChange);
        quotesData.push(weeklyDate);
        quotesData.push(weeklyPercentChange);
        quotesData.push(monthlyDate);
        quotesData.push(monthlyPercentChange);
        quotesData.push(annualDate);
        quotesData.push(annualPercentChange);

        var value = $('#val');
        value.text(currentCloseQuota);
        value = $('#daily');
        value.text(dailyPercentChange + "%");
        value = $('#open');
        value.text(currentOpenQuota);
        value = $('#high');
        value.text(currentHighQuota);
        value = $('#low');
        value.text(currentLowQuota);
        value = $('#weekly');
        value.text(weeklyPercentChange + "%");
        value = $('#monthly');
        value.text(monthlyPercentChange + "%");
        value = $('#annual');
        value.text(annualPercentChange + "%");
        showMyTable($('#stockDataQuotesInfo'));

        buildGraph(graphData, stockName);

        buildBuyThisItem(stockName);
    }

    var buildGraph = function(graphData, stockName){

        var table = $('#stockDataGraph');

        var dataQuotes = graphData.data;

        var feedData = [];

        var length = getLength(dataQuotes);
        for(var i = 0; i < length; i++ )
        {
            var quotes = dataQuotes[i];
            var date = quotes[0];
            var price = quotes[4];
            feedData[i] = new Array();
            feedData[i][0] = new Date(date);
            feedData[i][1] = Math.round(price * 100) / 100;
        }


        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Date');
        data.addColumn('number', stockName + " price");


        data.addRows(feedData);


        var options = {
            title: stockName + ' price history',
            width: 500,
            height: 300,
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
    }

    var prepareData = function(){

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

    var clearMyTable = function(element){
        element.find("tr:gt(0)").remove();
    }

    var hideMyTable = function(element){
        element.hide();
    }

    var showMyTable = function(element){
        element.show();
    }

    var getLength = function(obj){
        var len = 0;
        $.each(obj, function(i, elem) {
            len++;
        });
        return len;
    }

    var buildBuyThisItem = function(productName){
        $('<a>',{
            text: 'Buy this product',
            href: '/data/product/'+productName,
            class: 'btn btn-default btn-sm',
        }).appendTo('#buyHolder');
    }

    google.load('visualization', '1', {packages: ['corechart']});


</script>

</html>

