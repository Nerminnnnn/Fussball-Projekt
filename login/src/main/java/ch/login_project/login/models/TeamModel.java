package ch.login_project.login.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teams")
@Data
public class TeamModel {
    @Id
    private Long id;
    private String name;
}
