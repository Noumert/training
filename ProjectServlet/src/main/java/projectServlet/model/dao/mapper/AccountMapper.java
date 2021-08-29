package projectServlet.model.dao.mapper;

import projectServlet.model.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AccountMapper implements ObjectMapper<Account>{
    @Override
    public Account extractFromResultSet(ResultSet rs) throws SQLException {
        return Account.builder()
                .id(rs.getLong("account_id"))
                .accountName(rs.getString("account_name"))
                .accountNumber(rs.getString("account_number"))
                .ban(rs.getBoolean("ban"))
                .money(rs.getLong("money"))
                .build();
    }

    @Override
    public Account makeUnique(Map<Long, Account> cache, Account account) {
        cache.putIfAbsent(account.getId(), account);
        return cache.get(account.getId());
    }
}
