package project.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;

        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return 1545849159;
    }
}
