package projectServlet.model.service;

import projectServlet.model.dao.CreditCardDao;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 31.08.2021.
 */

public class CreditCardServiceImpl implements CreditCardService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void save(CreditCard creditCard) {
        try (CreditCardDao dao = daoFactory.createCreditCardDao()) {
            dao.save(creditCard);
        }
    }

    @Override
    public List<CreditCard> findUserCardsById(Long userId) {
        try (CreditCardDao dao = daoFactory.createCreditCardDao()) {
            return dao.findByUserId(userId);
        }
    }

    @Override
    public List<CreditCard> findAll() {
        try (CreditCardDao dao = daoFactory.createCreditCardDao()) {
            return dao.findAll();
        }
    }

    @Override
    public Optional<CreditCard> findByAccountId(Long accountId) {
        try (CreditCardDao dao = daoFactory.createCreditCardDao()) {
            return dao.findByAccountId(accountId);
        }
    }

}
