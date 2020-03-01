console.log("employeeProfile.js is working");

const decodedToken = sessionStorage.getItem("token").split("&");

let emailField = document.getElementById("email");
let passField = document.getElementById("password");
let confirmPWField = document.getElementById("confirmPassword");
emailField.value = decodedToken[2];
document.getElementById("resetFormBtn").addEventListener("click", resetForm);

function resetForm() {
    emailField.value = "";
    passField.value = "";
    confirmPWField.value = "";
}