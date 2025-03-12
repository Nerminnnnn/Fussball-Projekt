package ch.login_project.login.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String stadium;
    @Setter
    @Getter
    private String city;
    // Weitere relevante Attribute, je nach API-Antwort

    // Getter und Setter
}
