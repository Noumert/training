package project.model.entity;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name="user")
public class User{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean accountNonLocked;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;
}