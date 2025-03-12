$(document).ready(function () {
    loadUserInfo();
    loadFavorites();

    $('#competition-select').change(function () {
        var competitionId = $(this).val();
        if (competitionId) {
            loadTeams(competitionId);
            loadStandings(competitionId);
        } else {
            $('#standings-content').empty();
            $('#matches-content').empty();
            $('#standings-section').hide();
            $('#matches-section').hide();
            $('#teams-section').hide();
        }
    });

    $('#team-select').change(function () {
        var teamId = $(this).val();
        if (teamId) {
            $('#show-matches-button').show();
        } else {
            $('#show-matches-button').hide();
            $('#matches-content').empty();
            $('#matches-section').hide();
        }
    });

    function loadUserInfo() {
        $.ajax({
            url: '/api/userinfo',
            type: 'GET',
            success: function (user) {
                $('#username').text(user.username);
                $('#welcome-username').text(user.username);
            },
            error: function () {
                console.error('Fehler beim Laden der Benutzerinformationen.');
            }
        });
    }

    function loadStandings(competitionId) {
        $.ajax({
            url: '/api/standings/' + competitionId,
            type: 'GET',
            success: function (data) {
                var standingsHtml = '<table><tr><th>Position</th><th>Team</th><th>Punkte</th><th>Favorit</th></tr>';
                data.forEach(function (item) {
                    standingsHtml += '<tr><td>' + item.position + '</td><td>' + item.team + '</td><td>' + item.points + '</td>';
                    standingsHtml += '<td><span class="favorite-icon empty" onclick="toggleFavorite(' + item.id + ', this)"></span></td></tr>';
                });
                standingsHtml += '</table>';
                $('#standings-content').html(standingsHtml);
                $('#standings-section').show();
            },
            error: function () {
                console.error('Fehler beim Laden der Tabellen.');
            }
        });
    }

    function loadTeamMatches(teamId) {
        $.ajax({
            url: '/api/teams/' + teamId + '/matches',
            type: 'GET',
            success: function (data) {
                var matchesHtml = '<table><tr><th>Datum</th><th>Heimmannschaft</th><th>Auswärtsmannschaft</th><th>Status</th></tr>';
                data.forEach(function (match) {
                    var date = new Date(match.utcDate);
                    var formattedDate = date.toLocaleString('de-DE', {
                        day: 'numeric',
                        month: 'short',
                        year: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    });
                    matchesHtml += '<tr><td>' + formattedDate + '</td><td>' + match.homeTeam + '</td><td>' + match.awayTeam + '</td><td>' + match.status + '</td></tr>';
                });
                matchesHtml += '</table>';
                $('#matches-content').html(matchesHtml);
                $('#matches-section').show();
            },
            error: function () {
                console.error('Fehler beim Laden der Spiele.');
            }
        });
    }

    function loadTeams(competitionId) {
        $.ajax({
            url: '/api/teams/' + competitionId,
            type: 'GET',
            success: function (data) {
                var teamsHtml = '<option value="">Team auswählen</option>';
                data.forEach(function (team) {
                    teamsHtml += '<option value="' + team.id + '">' + team.name + '</option>';
                });
                $('#team-select').html(teamsHtml);
                $('#teams-section').show();
            },
            error: function () {
                console.error('Fehler beim Laden der Teams.');
            }
        });
    }

    function loadFavorites() {
        $.ajax({
            url: '/api/favorites',
            type: 'GET',
            success: function (data) {
                var favoritesHtml = '<ul>';
                data.forEach(function (team) {
                    favoritesHtml += '<li>' + team.name + ' <span class="favorite-icon filled" onclick="removeFromFavorites(' + team.id + ', this)"></span></li>';
                });
                favoritesHtml += '</ul>';
                $('#favorites-content').html(favoritesHtml);
            },
            error: function () {
                console.error('Fehler beim Laden der Favoriten.');
            }
        });
    }

    window.toggleFavorite = function (teamId, element) {
        if (element.classList.contains('empty')) {
            addToFavorites(teamId, element);
        } else {
            removeFromFavorites(teamId, element);
        }
    };

    window.addToFavorites = function (teamId, element) {
        $.ajax({
            url: '/api/favorites',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ teamId: teamId }),
            success: function () {
                console.log('Team zu Favoriten hinzugefügt');
                element.classList.remove('empty');
                element.classList.add('filled');
                loadFavorites();
            },
            error: function () {
                console.error('Fehler beim Hinzufügen zu Favoriten.');
            }
        });
    };

    window.removeFromFavorites = function (teamId, element) {
        $.ajax({
            url: '/api/favorites/' + teamId,
            type: 'DELETE',
            success: function () {
                console.log('Team aus Favoriten entfernt');
                element.classList.remove('filled');
                element.classList.add('empty');
                loadFavorites();
            },
            error: function () {
                console.error('Fehler beim Entfernen aus Favoriten.');
            }
        });
    };

    $('#search-input').on('input', function () {
        var searchTerm = $(this).val().toLowerCase();
        $('#competition-select option').each(function () {
            var text = $(this).text().toLowerCase();
            if (text.indexOf(searchTerm) === -1) {
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    });

    window.toggleProfileMenu = function () {
        $('#profile-menu').toggle();
    };

    window.logout = function () {
        window.location.href = '/logout';
    };

    $('#show-matches-button').click(function () {
        var teamId = $('#team-select').val();
        if (teamId) {
            loadTeamMatches(teamId);
        }
    });

    // Funktion zum Anzeigen des Formulars zum Ändern der Benutzerdaten
    window.showChangeCredentials = function () {
        $('#change-credentials-form').show();
    };

    // Funktion zum Aktualisieren des Benutzernamens
    window.updateUser = function () {
        var newUsername = $('#new-username').val();
        if (newUsername) {
            $.ajax({
                url: '/user/update-username',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ username: newUsername }),
                success: function (data) {
                    console.log('Benutzername erfolgreich aktualisiert');
                    loadUserInfo();
                    hideChangeCredentials();
                },
                error: function () {
                    console.error('Fehler beim Aktualisieren des Benutzers.');
                }
            });
        }
    };

    // Funktion zum Aktualisieren des Passworts
    window.updatePassword = function () {
        var newPassword = $('#new-password').val();
        if (newPassword) {
            $.ajax({
                url: '/user/update-password',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ password: newPassword }),
                success: function () {
                    console.log('Passwort erfolgreich aktualisiert');
                    hideChangeCredentials();
                },
                error: function () {
                    console.error('Fehler beim Aktualisieren des Passworts.');
                }
            });
        }
    };

    // Funktion zum Ausblenden des Formulars zum Ändern der Benutzerdaten
    window.hideChangeCredentials = function () {
        $('#change-credentials-form').hide();
        $('#new-username').val('');
        $('#new-password').val('');
    };

    $('#cancel-change-credentials').click(function () {
        hideChangeCredentials();
    });
});
