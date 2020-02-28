console.log("viewEmployees.js is working");

document.getElementById("registerEmployeeBtn").addEventListener("click", registerEmployee);

function registerEmployee(event) {
    event.preventDefault();

    let formData = $("#registerEmployeeForm").serialize();

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/api/employees";
    xhr.open("POST", url);

    xhr.onreadystatechange = function() {
        let message = document.getElementById("confirmEmployeeRegisteredBody");
        if (xhr.readyState == 4 && xhr.status == 200) {
            message.innerText = "A new employee has been registered, an email has been sent to notify the employee";
            document.getElementById("email").value = "";
            document.getElementById("first_name").value = "";
            document.getElementById("last_name").value = "";

            $("#confirmEmployeeRegistered").modal("show");
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            message.innerText = "registration failed";
            $("#confirmEmployeeRegistered").modal("show");
        }
    }

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(formData);
}