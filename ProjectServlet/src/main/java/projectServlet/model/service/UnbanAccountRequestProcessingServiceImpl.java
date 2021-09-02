package projectServlet.model.service;


import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.PaymentDao;
import projectServlet.model.dao.UnbanAccountRequestDao;
import projectServlet.model.dao.impl.ConnectionPoolHolder;
import projectServlet.model.dao.impl.JDBCAccountDao;
import projectServlet.model.dao.impl.JDBCPaymentDao;
import projectServlet.model.dao.impl.JDBCUnbanAccountRequestDao;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.StatusType;
import projectServlet.model.entity.UnbanAccountRequest;

import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Noumert on 21.08.2021.
 */
public class UnbanAccountRequestProcessingServiceImpl implements UnbanAccountRequestProcessingService {

    @Override
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        try(Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
            UnbanAccountRequestDao unbanAccountRequestDao = new JDBCUnbanAccountRequestDao(connection);
            AccountDao accountDao = new JDBCAccountDao(connection)){
            connection.setAutoCommit(false);
            try{
                unbanAccountRequest.setResolved(resolved);
                unbanAccountRequestDao.save(unbanAccountRequest);
                Account account = accountDao.findById(unbanAccountRequest.getAccount().getId()).orElseThrow(NotFoundException::new);
                account.setBan(ban);
                accountDao.save(account);
                connection.commit();
            }catch (Throwable e){
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
