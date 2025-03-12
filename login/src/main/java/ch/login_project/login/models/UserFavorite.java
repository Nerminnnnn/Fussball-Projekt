package ch.login_project.login.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_favorites")
@Data
@NoArgsConstructor
public class UserFavorite {

    @EmbeddedId
    private UserFavoriteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamModel team;

    public UserFavorite(UserEntity user, TeamModel team) {
        this.id = new UserFavoriteId(user.getId(), team.getId());
        this.user = user;
        this.team = team;
    }
}
