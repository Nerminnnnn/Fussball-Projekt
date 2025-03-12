$(document).ready(function () {
    $('#change-username-form').submit(function (event) {
        event.preventDefault();
        var newUsername = $('#new-username').val();

        $.ajax({
            url: '/api/change-username',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({username: newUsername}),
            success: function () {
                alert('Benutzername erfolgreich geändert');
            },
            error: function () {
                alert('Fehler beim Ändern des Benutzernamens');
            }
        });
    });

    $('#change-password-form').submit(function (event) {
        event.preventDefault();
        var currentPassword = $('#current-password').val();
        var newPassword = $('#new-password').val();

        $.ajax({
            url: '/api/change-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                currentPassword: currentPassword,
                newPassword: newPassword
            }),
            success: function () {
                alert('Passwort erfolgreich geändert');
            },
            error: function () {
                alert('Fehler beim Ändern des Passworts');
            }
        });
    });
});
