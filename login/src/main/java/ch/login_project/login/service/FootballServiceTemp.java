package ch.login_project.login.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FootballServiceTemp {

    @Value("${api.football.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.football-data.org/v4";

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiToken);
        return headers;
    }

    public List<Competition> getFootballCompetitions() {
        String url = apiUrl + "/competitions";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<Competition> competitions = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode competitionsNode = rootNode.get("competitions");
            if (competitionsNode.isArray()) {
                for (JsonNode node : competitionsNode) {
                    Competition competition = new Competition();
                    competition.setId(node.get("id").asText());
                    competition.setName(node.get("name").asText());
                    competitions.add(competition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return competitions;
    }

    public List<Team> getTeams(String competitionId) {
        String url = apiUrl + "/competitions/" + competitionId + "/teams";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<Team> teams = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode teamsNode = rootNode.get("teams");
            if (teamsNode.isArray()) {
                for (JsonNode node : teamsNode) {
                    Team team = new Team();
                    team.setId(node.get("id").asText());
                    team.setName(node.get("name").asText());
                    team.setStadium(node.get("venue").asText());
                    team.setCity(node.get("area").get("name").asText());
                    teams.add(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public Team getTeam(String teamId) {
        String url = apiUrl + "/teams/" + teamId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        Team team = new Team();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonResponse);
            team.setId(node.get("id").asText());
            team.setName(node.get("name").asText());
            team.setStadium(node.get("venue").asText());
            team.setCity(node.get("area").get("name").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return team;
    }

    public List<Standing> getStandings(String competitionId) {
        String url = apiUrl + "/competitions/" + competitionId + "/standings";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<Standing> standings = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode standingsNode = rootNode.get("standings");
            if (standingsNode.isArray()) {
                for (JsonNode node : standingsNode) {
                    JsonNode tableNode = node.get("table");
                    if (tableNode.isArray()) {
                        for (JsonNode teamNode : tableNode) {
                            Standing standing = new Standing();
                            standing.setId(teamNode.get("team").get("id").asText());
                            standing.setPosition(teamNode.get("position").asInt());
                            standing.setTeam(teamNode.get("team").get("name").asText());
                            standing.setPoints(teamNode.get("points").asInt());
                            standings.add(standing);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON response", e);
        }
        return standings;
    }

    public Standing getStanding(String standingId) {
        // Implementieren Sie die Logik, um eine einzelne Standing-Detail zu laden, wenn die API dies unterstützt
        // Dies könnte je nach API-Design anders sein
        return new Standing();
    }

    public List<Match> getMatches(String competitionId) {
        String url = apiUrl + "/competitions/" + competitionId + "/matches";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<Match> matches = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode matchesNode = rootNode.get("matches");
            if (matchesNode.isArray()) {
                for (JsonNode node : matchesNode) {
                    Match match = new Match();
                    match.setId(node.get("id").asText());
                    match.setUtcDate(node.get("utcDate").asText());
                    match.setStatus(node.get("status").asText());
                    match.setHomeTeam(node.get("homeTeam").get("name").asText());
                    match.setAwayTeam(node.get("awayTeam").get("name").asText());
                    matches.add(match);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON response", e);
        }
        return matches;
    }

    public Match getMatch(String matchId) {
        String url = apiUrl + "/matches/" + matchId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        Match match = new Match();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonResponse);
            match.setId(node.get("id").asText());
            match.setUtcDate(node.get("utcDate").asText());
            match.setStatus(node.get("status").asText());
            match.setHomeTeam(node.get("homeTeam").get("name").asText());
            match.setAwayTeam(node.get("awayTeam").get("name").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return match;
    }
    public List<Match> getMatchesForTeam(String teamId) {
        String url = apiUrl + "/teams/" + teamId + "/matches";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<Match> matches = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode matchesNode = rootNode.get("matches");
            if (matchesNode.isArray()) {
                for (JsonNode node : matchesNode) {
                    Match match = new Match();
                    match.setId(node.get("id").asText());
                    match.setUtcDate(node.get("utcDate").asText());
                    match.setStatus(node.get("status").asText());
                    match.setHomeTeam(node.get("homeTeam").get("name").asText());
                    match.setAwayTeam(node.get("awayTeam").get("name").asText());
                    matches.add(match);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON response", e);
        }
        return matches;
    }

}
