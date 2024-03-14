package Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cats_main_info")
public class CatsMainInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cat_id;
    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner ownerId;
    @Column(name = "cat_name")
    private String name;
    @Column(name = "cat_birthday")
    private LocalDate birthDate;
    @Column(name = "cat_breed")
    private String breed;
    @Column(name = "cat_color")
    @Enumerated(EnumType.STRING)
    private CatColor color;

    public CatsMainInfo() {
    }
}
