package Models;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OwnersCatsPrimaryKey implements Serializable {
    private Integer ownerId;
    private Integer catId;


    public OwnersCatsPrimaryKey(Integer ownerId, Integer catId) {
        this.ownerId = ownerId;
        this.catId = catId;
    }

    public OwnersCatsPrimaryKey() {
    }
}
