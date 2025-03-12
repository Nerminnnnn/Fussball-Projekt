package ch.login_project.login.controller;

import ch.login_project.login.models.TeamModel;
import ch.login_project.login.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/api/load-teams/{competitionId}")
    public String loadTeams(@PathVariable String competitionId) {
        teamService.fetchAndSaveTeams(competitionId);
        return "Teams loaded and saved to database";
    }

    @GetMapping("/api/teams")
    public List<TeamModel> getAllTeams() {
        return teamService.getAllTeams();
    }
}
