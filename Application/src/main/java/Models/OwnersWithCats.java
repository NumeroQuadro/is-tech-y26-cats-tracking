package Models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "owners_with_cats")
@Getter
@Setter
public class OwnersWithCats {
    @EmbeddedId
    private OwnerWithCatID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ownerId")
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("catId")
    @JoinColumn(name = "cat_id")
    private CatsMainInfo cat;

    public OwnersWithCats() { }
}
