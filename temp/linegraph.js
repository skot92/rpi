$(document).ready(function() {


    $.ajax({
        url: "http://169.254.71.227/temp/chart.php",
        type: "GET",
        dataType: "json",
        success: function(data) {
            console.log(data);

            var temp = [];
            var date_time = [];

            for (var i in data) {
                temp.push(data[i].temp);
                date_time.push(data[i].date_time);

            }

            console.log("temp lenggth");
            console.log(data[2].date_time);

            var chartdata = {
                labels: date_time,
                datasets: [{
                        label: "date_time",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(59, 89, 152, 0.75)",
                        borderColor: "rgba(59, 89, 152, 1)",
                        pointHoverBackgroundColor: "rgba(59, 89, 152, 1)",
                        pointHoverBorderColor: "rgba(59, 89, 152, 1)",
                        data: temp
                    }

                ]
            };

            var ctx = $("#mycanvas");

            var LineGraph = new Chart(ctx, {
                type: 'line',
                data: chartdata
            });
        },
        error: function(data) {

        }
    });
});