package projectServlet.model.dao.mapper;

import projectServlet.model.entity.CreditCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CreditCardMapper implements ObjectMapper<CreditCard>{
    @Override
    public CreditCard extractFromResultSet(ResultSet rs) throws SQLException {
        return CreditCard.builder()
                .id(rs.getLong("credit_card_id"))
                .cardNumber(rs.getString("card_number"))
                .expirationDate(rs.getDate("expiration_date").toLocalDate())
                .build();
    }

    @Override
    public CreditCard makeUnique(Map<Long, CreditCard> cache, CreditCard creditCard) {
        cache.putIfAbsent(creditCard.getId(), creditCard);
        return cache.get(creditCard.getId());
    }
}
