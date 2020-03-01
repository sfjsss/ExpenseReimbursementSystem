const decodedToken = sessionStorage.getItem("token").split("&");

getAllResolvedReimbursements();

function getAllResolvedReimbursements() {
    const url = "http://localhost:8080/api/reimbursements?employeeId=" + decodedToken[0] + "&type=resolved";
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const parsedData = JSON.parse(xhr.response);
            console.log(parsedData);
            for (let reimbursement of parsedData) {
                renderReimbursement(reimbursement);
            }
        } 
    }
    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    xhr.send();
}

function renderReimbursement(reimbursement) {
    const tableBody = document.getElementById("requestsTable");
    let tableRow = document.createElement("tr");
    tableRow.innerHTML = `<td>${reimbursement.reimbursement_id}</td>
                            <td>${reimbursement.reimbursement_type}</td>
                            <td>${reimbursement.reimbursement_time}</td>
                            <td>$${reimbursement.reimbursement_amount}</td>
                            <td><a href="http://127.0.0.1:8887${reimbursement.receipt_path}" target="_blank">${reimbursement.receipt_name}</a></td>
                            <td>${reimbursement.reimbursement_description}</td>
                            <td>${reimbursement.reimbursement_status} by ${reimbursement.processor.first_name} ${reimbursement.processor.last_name}</td>`;
    tableBody.appendChild(tableRow);
}