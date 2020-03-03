$(document).ready(function() {
    const decodedToken = sessionStorage.getItem("token");
    let xAxis = [];
    let yAxis1 = [];
    let yAxis2 = [];
    getAllReimbursements();

    // chart.js
    var ctx = document.getElementById('myChart').getContext('2d');
    var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',

    // The data for our dataset
    data: {
        labels: xAxis.reverse(),
        datasets: [{
            label: 'Certification',
            backgroundColor: 'rgba(255, 99, 132, 0)',
            borderColor: 'rgb(255, 99, 132)',
            data: yAxis1.reverse()
        },
        {
            label: 'Relocation',
            backgroundColor: 'rgba(255, 99, 132, 0)',
            borderColor: 'rgb(161, 239, 139)',
            data: yAxis2.reverse()
        }]
    },

    // Configuration options go here
    options: {}
    });

    function getAllReimbursements() {
        const url = `http://localhost:8080/api/reimbursements?employeeId=${decodedToken[0]}&type=resolved`;
        let xhr = new XMLHttpRequest();

        xhr.open("GET", url, false);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                const parsedData = JSON.parse(xhr.response);
                for (let reimbursement of parsedData) {
                    if (reimbursement.reimbursement_status == "approved") {
                        renderData(reimbursement);
                    }
                }
            }
        }
        xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
        xhr.send();
    }

    function renderData(reimbursement) {
        xAxis.push(reimbursement.reimbursement_time);
        if (reimbursement.reimbursement_type == 'certification') {
            yAxis1.push({
                x: reimbursement.reimbursement_time,
                y: reimbursement.reimbursement_amount
            });
        } else {
            yAxis2.push({
                x: reimbursement.reimbursement_time,
                y: reimbursement.reimbursement_amount
            });
        }
    }
});
