$(document).ready(function() {
    let params = (new URL(document.location)).searchParams;
    console.log(params);
    $('#confirmProfileUpdate').modal('show')
})