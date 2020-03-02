console.log("managerResolved.js is working");

const searchParams = (new URL(window.location).searchParams);
let employeeId;
let employeeFirstName;
let employeeLastName;
let searchField = document.getElementById("employeeName");
if (searchParams.get("employeeId") != null) {
    employeeId = searchParams.get("employeeId")
    employeeFirstName = searchParams.get("first_name");
    employeeLastName = searchParams.get("last_name");
    searchField.value = employeeFirstName + " " + employeeLastName;
    getAllResolvedReimbursementsById(employeeId);
} else {
    getAllResolvedReimbursements();
}

function getAllResolvedReimbursements() {
    const url = "http://localhost:8080/api/reimbursements?employeeId=0&type=resolved";
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 & xhr.status == 200) {
            const parsedData = JSON.parse(xhr.response);
            for (let reimbursement of parsedData) {
                renderReimbursement(reimbursement);
            }
        }
    }
    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    xhr.send();
}

function getAllResolvedReimbursementsById(employeeId) {
    const url = `http://localhost:8080/api/reimbursements?employeeId=${employeeId}&type=resolved`;
    console.log(url);
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const parsedData = JSON.parse(xhr.response);
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
                            <td>${reimbursement.requester.first_name} ${reimbursement.requester.last_name}</td>
                            <td>${reimbursement.reimbursement_status} by ${reimbursement.processor.first_name} ${reimbursement.processor.last_name}</td>`;
    tableBody.appendChild(tableRow);
}