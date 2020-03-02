$(document).ready(function() {
    console.log("passwordReset is working");

    let emailField = document.getElementById("email");
    let submitBtn = document.getElementById("resetSubmitBtn");
    submitBtn.addEventListener("click", resetPassword);
    emailField.addEventListener("change", resetFormValidation);

    function resetFormValidation() {
        if (emailField.value != "") {
            submitBtn.disabled = false;
        }
    }

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

