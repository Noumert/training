package projectServlet.model.service;

import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.entity.Account;

import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface AccountService {

    void save(Account account);

    List<Account> findByUserId(Long userId);

    List<Account> findByUserId(Long userId, int page, int pageSize, String sortBy, boolean asc);

    List<Account> findAll();

    Optional<Account> findById(Long accountId);

    List<Account> findFreeUserAccountsByUserId(Long userId);

    void setBanByAccount(boolean ban, Account account);

    void addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException;
}
