console.log("viewEmployees.js is working");

getAllEmployees();
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
            document.getElementById("email").value = "";
            document.getElementById("first_name").value = "";
            document.getElementById("last_name").value = "";

            message.innerText = "A new employee has been registered, an email has been sent to notify the employee";
            $("#confirmEmployeeRegistered").modal("show");
            getAllEmployees();
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            message.innerText = "registration failed";
            $("#confirmEmployeeRegistered").modal("show");
        }
    }

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    xhr.send(formData);
}

function getAllEmployees() {
    const url = "http://localhost:8080/api/employees";
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const parsedData = JSON.parse(xhr.response);
            for (let employee of parsedData) {
                renderEmployees(employee);
            }
        }
    }
    xhr.setRequestHeader("authorization", sessionStorage.getItem("token"));
    xhr.send();
}

function renderEmployees(employee) {
    const tableBody = document.getElementById("employeesTable");
    let tableRow = document.createElement("tr");
    tableRow.innerHTML = `<td>${employee.employee_id}</td>
                            <td>${employee.first_name} ${employee.last_name}</td>
                            <td>${employee.email}</td>
                            <td><a href="manager-pending?employeeId=${employee.employee_id}&first_name=${employee.first_name}&last_name=${employee.last_name}">view pending</a> | <a href="manager-resolved?employeeId=${employee.employee_id}&first_name=${employee.first_name}&last_name=${employee.last_name}">view resolved</a></td>`;
    tableBody.appendChild(tableRow);
}