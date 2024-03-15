package Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(CatsWithFriendsPrimaryKey.class)
@Table(name = "cats_friends")
public class CatsWithFriends {
    @Id
    @Column(name = "cat_id")
    private Integer catId;
    @Column(name = "friend_id")
    private Integer friendId;
}
