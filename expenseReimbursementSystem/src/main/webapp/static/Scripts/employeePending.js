$(document).ready(function() {
    bsCustomFileInput.init();

    const searchParams = (new URL(window.location).searchParams);
    console.log(searchParams);
    if (searchParams.get("submission") == "true") {
        $("#confirmReimSubmission").modal("show");
    }
    
    const decodedToken = sessionStorage.getItem("token").split("&");
    document.getElementById("formUserId").value = decodedToken[0];
    document.getElementById("formUserEmail").value = decodedToken[2];

    getAllPendingReimbursements();

    function getAllPendingReimbursements() {
        const url = "http://localhost:8080/api/reimbursements?employeeId=" + decodedToken[0] + "&type=pending";
        let xhr = new XMLHttpRequest();

        xhr.open("GET", url);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                const parsedData = JSON.parse(xhr.response);
                console.log(parsedData);
            } 
        }
        xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
        xhr.send();
    }
})