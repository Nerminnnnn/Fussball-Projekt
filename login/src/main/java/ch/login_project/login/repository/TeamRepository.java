package ch.login_project.login.repository;

import ch.login_project.login.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamModel, Long> {
}
