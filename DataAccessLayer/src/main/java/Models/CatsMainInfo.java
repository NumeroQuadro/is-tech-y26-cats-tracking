package Models;

import Models.Enums.CatColor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cats_main_info")
public class CatsMainInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cat_id;
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
