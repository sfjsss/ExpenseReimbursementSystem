$(document).ready(function() {
    console.log("passwordReset is working");

    document.getElementById("resetSubmitBtn").addEventListener("click", resetPassword);

    function resetPassword(event) {
        event.preventDefault();
        const email = document.getElementById("email").value;
        const url = `http://localhost:8080/reset-password?email=${email}`;
        let xhr = new XMLHttpRequest();

        xhr.open("POST", url);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                $("#confirmResetSubmission").modal("show");
            }
        }
        xhr.send();
    }
});

