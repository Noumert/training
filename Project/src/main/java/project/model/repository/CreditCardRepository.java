package project.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByUserId(Long userId);
    Optional<CreditCard> findByAccountId(Long accountId);

//@Query("select new project.model.dto.UserAccountDTO(u.email,u.firstName,u.lastName,c.id,c.cardNumber,c.active,c.money,c.expirationDate) " +
//        " from CreditCard c join c.user u  where u.accountNonLocked=1")
//    List<UserAccountDTO> findAllUserCard();
}
