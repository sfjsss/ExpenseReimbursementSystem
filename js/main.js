$(document).ready(function() {
    console.log("jquery is rdy");
    bsCustomFileInput.init();

    // chart
    var ctx = document.getElementById('myChart').getContext('2d');
    var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',

    // The data for our dataset
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
            label: 'My First dataset',
            backgroundColor: 'rgba(255, 99, 132, 0)',
            borderColor: 'rgb(255, 99, 132)',
            data: [0, 10, 5, 2, 20, 30, 45]
        },
        {
            label: 'My Second dataset',
            backgroundColor: 'rgba(255, 99, 132, 0)',
            borderColor: 'rgb(161, 239, 139)',
            data: [1, 2, 3, 4, 5, 6, 7]
        }]
    },

    // Configuration options go here
    options: {}
    });
})