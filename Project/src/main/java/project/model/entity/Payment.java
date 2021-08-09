package project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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
    private String PaymentNumber;
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
