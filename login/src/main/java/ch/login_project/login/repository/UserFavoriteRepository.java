package ch.login_project.login.repository;

import ch.login_project.login.models.TeamModel;
import ch.login_project.login.models.UserEntity;
import ch.login_project.login.models.UserFavorite;
import ch.login_project.login.models.UserFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoriteId> {
    List<UserFavorite> findAllByUser(UserEntity user);
    Optional<UserFavorite> findByUserAndTeam(UserEntity user, TeamModel team);
    void deleteByUserAndTeam(UserEntity user, TeamModel team);
}
