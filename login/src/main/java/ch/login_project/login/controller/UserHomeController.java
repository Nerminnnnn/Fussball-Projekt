package ch.login_project.login.controller;

import ch.login_project.login.dto.FavoriteRequest;

import ch.login_project.login.models.TeamModel;
import ch.login_project.login.service.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class UserHomeController {

    private final FootballService footballService;
    private final UserFavoriteService userFavoriteService;


    @Autowired
    private UserService userService;

    @Autowired
    public UserHomeController(FootballService footballService, UserFavoriteService userFavoriteService) {
        this.footballService = footballService;
        this.userFavoriteService = userFavoriteService;
    }

    @GetMapping("/userhome")
    public ModelAndView userhome(@AuthenticationPrincipal UserDetails user) {
        ModelAndView page = new ModelAndView("userhome");
        page.addObject("user", user);

        List<Competition> competitions = footballService.getFootballCompetitions();
        page.addObject("competitions", competitions);

        return page;
    }


    @GetMapping("/api/competitions")
    @ResponseBody
    public List<Competition> getCompetitions() {
        return footballService.getFootballCompetitions();
    }

    @GetMapping("/api/teams/{competitionId}")
    @ResponseBody
    public List<Team> getTeams(@PathVariable String competitionId) {
        return footballService.getTeams(competitionId);
    }

    @GetMapping("/api/team/{teamId}")
    @ResponseBody
    public Team getTeam(@PathVariable String teamId) {
        return footballService.getTeam(teamId);
    }

    @GetMapping("/api/standings/{competitionId}")
    @ResponseBody
    public List<Standing> getStandings(@PathVariable String competitionId) {
        return footballService.getStandings(competitionId);

    }
    @PutMapping("/user/update-username")
    public ResponseEntity<String> updateUsername(@RequestBody Map<String, String> request) {
        String newUsername = request.get("username");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();

        userService.updateUsername(currentUsername, newUsername);

        return ResponseEntity.ok("Username updated successfully");
    }

    @PutMapping("/user/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();

        userService.updatePassword(currentUsername, newPassword);

        return ResponseEntity.ok("Password updated successfully");
    }


    @GetMapping("/api/standing/{standingId}")
    @ResponseBody
    public Standing getStanding(@PathVariable String standingId) {
        return footballService.getStanding(standingId);
    }

    @GetMapping("/api/matches/{competitionId}")
    @ResponseBody
    public List<Match> getMatches(@PathVariable String competitionId) {
        return footballService.getMatches(competitionId);
    }

    @GetMapping("/api/match/{matchId}")
    @ResponseBody
    public Match getMatch(@PathVariable String matchId) {
        return footballService.getMatch(matchId);
    }

    @GetMapping("/api/userinfo")
    @ResponseBody
    public UserDetails getUserInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @PostMapping("/api/favorites")
    @ResponseBody
    public void addFavorite(@RequestBody FavoriteRequest request, @AuthenticationPrincipal UserDetails user) {
        userFavoriteService.addFavorite(user, request.getTeamId());
    }

    @DeleteMapping("/api/favorites/{teamId}")
    @ResponseBody
    public void removeFavorite(@PathVariable Long teamId, @AuthenticationPrincipal UserDetails user) {
        userFavoriteService.removeFavorite(user, teamId);
    }

    @GetMapping("/api/favorites")
    @ResponseBody
    public List<TeamModel> getFavorites(@AuthenticationPrincipal UserDetails user) {
        return userFavoriteService.getFavorites(user);
    }
    @GetMapping("/api/teams/{teamId}/matches")
    @ResponseBody
    public List<Match> getTeamMatches(@PathVariable String teamId) {
        return footballService.getMatchesForTeam(teamId);
    }

}

