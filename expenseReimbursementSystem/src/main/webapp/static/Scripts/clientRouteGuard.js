console.log("clientRouteGuard.js is working");

let token = sessionStorage.getItem("token");

if (!token) {
    window.location.href = "http://localhost:8080/login";
} else {
    renderUserInfo();
}

function renderUserInfo() {
    const decodedToken = token.split("&");
    let navDropDown = document.getElementById("navDropDown");
    navDropDown.innerText = "Welcome " + decodedToken[3];
}