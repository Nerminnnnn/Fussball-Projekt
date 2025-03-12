package ch.login_project.login.service;

import ch.login_project.login.models.TeamModel;
import ch.login_project.login.repository.TeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TeamService {

    @Value("${api.football.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final TeamRepository teamRepository;
    private final String apiUrl = "https://api.football-data.org/v4";

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiToken);
        return headers;
    }

    public void fetchAndSaveTeams(String competitionId) {
        String url = apiUrl + "/competitions/" + competitionId + "/teams";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String jsonResponse = response.getBody();

        List<TeamModel> teams = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode teamsNode = rootNode.get("teams");
            if (teamsNode.isArray()) {
                for (JsonNode node : teamsNode) {
                    TeamModel team = new TeamModel();
                    team.setId(node.get("id").asLong());
                    team.setName(node.get("name").asText());
                    teams.add(team);
                    teamRepository.save(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<TeamModel> getAllTeams() {
        return teamRepository.findAll();
    }
}
