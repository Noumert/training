package project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.entity.CreditCard;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 11.08.2021.
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByUserId(Long userId);
    Optional<CreditCard> findByAccountId(Long accountId);

}
