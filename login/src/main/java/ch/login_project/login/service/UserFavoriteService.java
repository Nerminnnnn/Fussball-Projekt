package ch.login_project.login.service;

import ch.login_project.login.models.TeamModel;
import ch.login_project.login.models.UserEntity;
import ch.login_project.login.models.UserFavorite;
import ch.login_project.login.models.UserFavoriteId;
import ch.login_project.login.repository.TeamRepository;
import ch.login_project.login.repository.UserFavoriteRepository;
import ch.login_project.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<TeamModel> getFavorites(UserDetails user) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(user.getUsername());
        return userEntity.map(u -> u.getFavoriteTeams().stream()
                        .map(UserFavorite::getTeam)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public void addFavorite(UserDetails user, Long teamId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(user.getUsername());
        Optional<TeamModel> teamOptional = teamRepository.findById(teamId);

        if (userEntityOptional.isPresent() && teamOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            TeamModel team = teamOptional.get();
            if (userFavoriteRepository.findByUserAndTeam(userEntity, team).isEmpty()) {
                UserFavorite favorite = new UserFavorite(userEntity, team);
                userFavoriteRepository.save(favorite);
                System.out.println("Favorit erfolgreich gespeichert: " + favorite);
            } else {
                System.out.println("Favorit existiert bereits.");
            }
        } else {
            System.out.println("Nutzer oder Team nicht gefunden.");
        }
    }

    public void removeFavorite(UserDetails user, Long teamId) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(user.getUsername());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userFavoriteRepository.findByUserAndTeam(userEntity, teamRepository.findById(teamId).orElse(null)).ifPresent(userFavoriteRepository::delete);
        }
    }
}
