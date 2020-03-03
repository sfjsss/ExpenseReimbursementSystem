$(document).ready(function() {
    bsCustomFileInput.init();

    const searchParams = (new URL(window.location).searchParams);
    if (searchParams.get("submission") == "true") {
        $("#confirmReimSubmission").modal("show");
    }
    
    const decodedToken = sessionStorage.getItem("token").split("&");
    document.getElementById("formUserId").value = decodedToken[0];
    document.getElementById("formUserEmail").value = decodedToken[2];

    let reimType = document.getElementById("type");
    let reimDate = document.getElementById("date");
    let reimAmount = document.getElementById("amount");
    let submitBtn = document.getElementById("submitReimBtn");

    reimType.addEventListener("change", formValidation);
    reimDate.addEventListener("change", formValidation);
    reimAmount.addEventListener("change", formValidation);

    getAllPendingReimbursements();

    function getAllPendingReimbursements() {
        const url = "http://localhost:8080/api/reimbursements?employeeId=" + decodedToken[0] + "&type=pending";
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
                                <td>${reimbursement.reimbursement_description}</td>`;
        tableBody.appendChild(tableRow);
    }

    function formValidation() {
        if (reimType.value != "" && reimDate.value != "" && reimAmount.value != "") {
            submitBtn.disabled = false;
        } else {
            submitBtn.disabled = true;
        }
    }
})