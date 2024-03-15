package Models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "owners_with_cats")
@Getter
@Setter
@IdClass(OwnersCatsPrimaryKey.class)
public class OwnersWithCats {
    @Column(name = "owner_id")
    private Integer ownerId;
    @Id
    @Column(name = "cat_id")
    private Integer catId;

    public OwnersWithCats() { }
}
