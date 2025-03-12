package ch.login_project.login.service;

import lombok.Getter;
import lombok.Setter;

public class Standing {
    private int position;
    private String team;
    private int points;
    @Getter
    @Setter
    private String id;

    // Getter und Setter
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
