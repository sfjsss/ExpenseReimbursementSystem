console.log("login.js is working");

document.getElementById("loginBtn").addEventListener("click", requestLogin);

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

