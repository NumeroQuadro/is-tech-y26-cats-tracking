package Models;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CatsWithFriendsPrimaryKey implements Serializable {
    private Integer catId;
    private Integer friendId;

    public CatsWithFriendsPrimaryKey(Integer catId, Integer friendId) {
        this.catId = catId;
        this.friendId = friendId;
    }

    public CatsWithFriendsPrimaryKey() {
    }
}
