console.log("employeeProfile.js is working");

const decodedToken = sessionStorage.getItem("token").split("&");

let employeeIdField = document.getElementById("employeeId");
let emailField = document.getElementById("email");
let firstNameField = document.getElementById("first_name");
let lastNameField = document.getElementById("last_name");
let passField = document.getElementById("password");
let confirmPWField = document.getElementById("confirmPassword");

employeeIdField.value = decodedToken[0];
emailField.value = decodedToken[2];
firstNameField.value = decodedToken[3];
lastNameField.value = decodedToken[4];

document.getElementById("resetFormBtn").addEventListener("click", resetForm);
document.getElementById("updateProfileSubmitBtn").addEventListener("click", updateEmployeeProfile);

function resetForm() {
    emailField.value = "";
    firstNameField.value = "";
    lastNameField.value = "";
    passField.value = "";
    confirmPWField.value = "";
}

function updateEmployeeProfile(event) {
    event.preventDefault();
    
    let updatedEmployee = {
        employee_id: decodedToken[0],
        email: emailField.value,
        employee_type: "associate",
        first_name: firstNameField.value,
        last_name: lastNameField.value,
        pass: passField.value
    }

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/api/employee";
    xhr.open("PUT", url);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("updateResult").innerText = "You profile has been updated.";
            $("#confirmProfileUpdate").modal("show");
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            document.getElementById("updateResult").innerText = "Profile update failed.";
            $("#confirmProfileUpdate").modal("show");
        }
    }

    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    parsedData = JSON.stringify(updatedEmployee);
    xhr.send(parsedData);
}