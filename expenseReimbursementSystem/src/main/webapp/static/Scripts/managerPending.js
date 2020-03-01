console.log("managerPending.js is working");

const decodedToken = sessionStorage.getItem("token");
const managerId = decodedToken[0];

getAllPendingReimbursements();

function getAllPendingReimbursements() {
    const url = "http://localhost:8080/api/reimbursements?employeeId=0&type=pending";
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

function updateReimbursement(reimbursementId, operation) {
    const url = `http://localhost:8080/api/reimbursement?managerId=${managerId}&reimbursementId=${reimbursementId}&operation=${operation}`;
    let xhr = new XMLHttpRequest();

    xhr.open("PUT", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            window.location.href = "http://localhost:8080/manager-pending";
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
                            <td><a href="#" onclick="updateReimbursement(${reimbursement.reimbursement_id}, 'approved')">APPROVE</a> | <a href="#" onclick="updateReimbursement(${reimbursement.reimbursement_id}, 'denied')">DENY</a></td>`;
    tableBody.appendChild(tableRow);
}

