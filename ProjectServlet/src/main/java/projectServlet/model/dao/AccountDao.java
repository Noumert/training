package projectServlet.model.dao;

import projectServlet.model.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends GenericDao<Account>{
    List<Account> findByUserId(Long userId);

//    Page<Account> findByUserId(Long userId, Pageable pageable);

    Optional<Account> findByAccountName(String accountName);

    List<Account> findFreeUserAccountsByUserId(Long userId);

    List<Account> findByUserId(Long userId, int page, int pageSize, String sortBy, boolean asc);
}
