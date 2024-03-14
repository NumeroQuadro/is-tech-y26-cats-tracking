package Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "owners_with_cats")
@Getter
@Setter
public class OwnersWithCats {
    @Id
    @JoinColumn(name = "cat_id")
    private Integer cat_id;
    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner ownerId;
}
