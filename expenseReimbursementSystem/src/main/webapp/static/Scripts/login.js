console.log("login.js is working");

let emailField = document.getElementById("email");
let passwordField = document.getElementById("password");
let loginBtn = document.getElementById("loginBtn");

loginBtn.addEventListener("click", requestLogin);
emailField.addEventListener("change", loginFormValidation);
passwordField.addEventListener("change", loginFormValidation);

function loginFormValidation() {
    let emailField = document.getElementById("email");
    let passwordField = document.getElementById("password");
    let loginBtn = document.getElementById("loginBtn");
    if (emailField.value != "" && passwordField.value != "") {
        loginBtn.disabled = false;
    } else {
        loginBtn.disabled = true;
    }
}

function requestLogin(event) {
    event.preventDefault();
    let alert = document.getElementById("loginAlert");
    alert.hidden = true;
    
    let formData = $("#loginForm").serialize();

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/authenticate";
    xhr.open("POST", url);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let auth = xhr.getResponseHeader("authorization");
            if (auth != null) {
                sessionStorage.setItem("token", auth);
                let decodedToken = auth.split("&");
            
                if (decodedToken[1] == "associate") {
                    window.location.href = "http://localhost:8080/employee-pending";
                } else {
                    window.location.href = "http://localhost:8080/manager-pending";
                }
            }
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            console.log("invalid credential");
            alert.hidden = false;
        }
    }

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(formData);
}

