function register() {

    // 1. Get input values
    var input1 = $('#username').val();
    var input2 = $('#password').val();

    // 2. Create data object to send to the server
    var data = {
        username: input1,
        password: input2
    };
    console.log("button-clicked");
    // 3. Send POST request to server
    $.ajax({
        url: '/auth/register', // choose correct endpoint
        type: 'POST', // choose correct type: GET, POST, DELETE
        contentType: 'application/json', // Set content type to JSON
        data: JSON.stringify(data), // Convert data object to JSON string
        success: function (response) {
            window.location.replace("http://localhost:8080/auth/registration-success");
        },
        error: function (xhr, status, error) {
            console.log("rejected");
            // show result to user
            $('#createResponse').text(xhr.responseText);
        }
    });
}

function login() {

    // 1. Get input values
    var input1 = $('#login_username').val();
    var input2 = $('#login_password').val();

    // 2. Create data object to send to the server
    var data = {
        username: input1,
        password: input2
    };

    // 3. Send POST request to server
    $.ajax({
        url: '/auth/login', // choose correct endpoint
        type: 'POST', // choose correct type: GET, POST, DELETE
        contentType: 'application/json', // Set content type to JSON
        data: JSON.stringify(data), // Convert data object to JSON string
        success: function (response) {
            // $('#loginResponse').text(JSON.stringify(response));
            window.location.replace("http://localhost:8080/userhome");
        },
        error: function (xhr, status, error) {
            // show result to user
            $('#loginResponse').text(xhr.responseText);
            console.log(xhr);
        }
    });
}

function logout() {
       console.log("logging out");
    // 3. Send POST request to server
    $.ajax({
        url: '/logout', // choose correct endpoint
        type: 'POST', // choose correct type: GET, POST, DELETE
        contentType: 'application/json', // Set content type to JSON
        success: function (response) {
                    // window.location.replace("http://localhost:8080/api/auth/registration-success");
           $('#logoutResponse').text(JSON.stringify(response));
           window.location.replace("http://localhost:8080/auth/logout-success");
        }
    });
}