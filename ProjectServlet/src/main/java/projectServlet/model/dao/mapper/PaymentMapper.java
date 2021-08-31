package projectServlet.model.dao.mapper;

import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PaymentMapper implements ObjectMapper<Payment>{
    @Override
    public Payment extractFromResultSet(ResultSet rs) throws SQLException {
        return Payment.builder()
                .id(rs.getLong("payment_id"))
                .paymentNumber(rs.getString("payment_number"))
                .dateTime( rs.getTimestamp("date_time").toLocalDateTime())
                .recipient(rs.getString("recipient"))
                .status(StatusType.valueOf(rs.getString("status")))
                .money(rs.getLong("payment_money"))
                .build();
    }

    @Override
    public Payment makeUnique(Map<Long, Payment> cache, Payment payment) {
        cache.putIfAbsent(payment.getId(), payment);
        return cache.get(payment.getId());
    }
}
