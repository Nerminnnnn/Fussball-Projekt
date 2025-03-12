package ch.login_project.login.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {
    private List<TeamStanding> teamStandings;
    // Weitere relevante Attribute, je nach API-Antwort

    public List<TeamStanding> getTeamStandings() {
        return teamStandings;
    }

    public void setTeamStandings(List<TeamStanding> teamStandings) {
        this.teamStandings = teamStandings;
    }

    public void setType(String type) {
    }

    // Getter und Setter
}
