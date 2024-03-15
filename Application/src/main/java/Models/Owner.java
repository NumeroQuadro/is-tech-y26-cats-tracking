package Models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "owner_id")
    private Integer id;
    @Column(name = "owner_name")
    private String name;
    @Column(name = "owner_birthday")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OwnersWithCats> cats = new HashSet<>();

    public Owner() {
    }
}
