package ba.sum.fsre.hepoc.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Citizen> citizens;
}
