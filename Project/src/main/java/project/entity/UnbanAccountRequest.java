package project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Noumert on 12.08.2021.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UnbanAccountRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private boolean resolved;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
