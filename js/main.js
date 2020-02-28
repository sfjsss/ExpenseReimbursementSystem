console.log("main.js is working");

let token = sessionStorage.getItem("token");
let user = {};

if (!token) {
    window.location.href = "http://localhost:8080/login";
} else {
    getUserInfo();
}

function getUserInfo() {
    let xhr = new XMLHttpRequest();
    let decodedToken = token.split(":");
    xhr.open("GET", "http://localhost:8080/api/employee/" + decodedToken[0]);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            user = JSON.parse(xhr.response);
        } else {
            console.log("didn't get user info back");
        }
    }
}