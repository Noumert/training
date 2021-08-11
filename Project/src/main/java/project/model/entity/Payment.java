package project.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String paymentNumber;
    @Column(nullable = false)
    private Long money;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
