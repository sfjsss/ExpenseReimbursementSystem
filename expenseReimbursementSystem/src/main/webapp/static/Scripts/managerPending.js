console.log("managerPending.js is working");

const decodedToken = sessionStorage.getItem("token");
const managerId = decodedToken[0];

document.getElementById("resetFilterBtn").addEventListener("click", resetFilter);
document.getElementById("filterBtn").addEventListener("click", filterResultsByName);

const searchParams = (new URL(window.location).searchParams);
let employeeId;
let employeeFirstName;
let employeeLastName;
let searchField = document.getElementById("employeeName");

retrieveInformation();

function retrieveInformation() {
    if (searchParams.get("employeeId") != null) {
        employeeId = searchParams.get("employeeId")
        employeeFirstName = searchParams.get("first_name");
        employeeLastName = searchParams.get("last_name");
        searchField.value = employeeFirstName + " " + employeeLastName;
        getAllPendingReimbursementsById(employeeId);
    } else {
        getAllPendingReimbursements();
    }
}

function getAllPendingReimbursements() {
    const url = "http://localhost:8080/api/reimbursements?employeeId=0&type=pending";
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

function getAllPendingReimbursementsById(employeeId) {
    const url = `http://localhost:8080/api/reimbursements?employeeId=${employeeId}&type=pending`;
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

function updateReimbursement(reimbursementId, operation) {
    const url = `http://localhost:8080/api/reimbursement?managerId=${managerId}&reimbursementId=${reimbursementId}&operation=${operation}`;
    let xhr = new XMLHttpRequest();

    xhr.open("PUT", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("requestsTable").innerHTML = "";
            retrieveInformation();
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
                            <td><a href="#" onclick="updateReimbursement(${reimbursement.reimbursement_id}, 'approved')">APPROVE</a> | <a href="#" onclick="updateReimbursement(${reimbursement.reimbursement_id}, 'denied')">DENY</a></td>`;
    tableBody.appendChild(tableRow);
}

function resetFilter() {
    window.location.href = "manager-pending";
}

function filterResultsByName(event) {
    event.preventDefault();
    const splittedName = searchField.value.split(" ");
    const first_name = splittedName[0];
    const last_name = splittedName[1];
    const url = `http://localhost:8080/api/reimbursements?employeeId=-1&type=pending&first_name=${first_name}&last_name=${last_name}`;
    console.log(url);
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const parsedData = JSON.parse(xhr.response);
            document.getElementById("requestsTable").innerHTML = "";
            for (let reimbursement of parsedData) {
                renderReimbursement(reimbursement);
            }
        }
    }
    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    xhr.send();
}

