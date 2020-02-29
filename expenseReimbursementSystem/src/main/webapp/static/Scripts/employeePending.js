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

})