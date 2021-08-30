package projectServlet.model.dao.mapper;

import projectServlet.model.dao.UnbanAccountRequestDao;
import projectServlet.model.entity.UnbanAccountRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UnbanAccountRequestMapper implements ObjectMapper<UnbanAccountRequest>{
    @Override
    public UnbanAccountRequest extractFromResultSet(ResultSet rs) throws SQLException {
        return UnbanAccountRequest.builder()
                .id(rs.getLong("unban_account_request_id"))
                .dateTime( rs.getTimestamp("date_time").toLocalDateTime())
                .resolved(rs.getBoolean("resolved"))
                .build();
    }

    @Override
    public UnbanAccountRequest makeUnique(Map<Long, UnbanAccountRequest> cache,
                                          UnbanAccountRequest unbanAccountRequest) {
        cache.putIfAbsent(unbanAccountRequest.getId(), unbanAccountRequest);
        return cache.get(unbanAccountRequest.getId());
    }
}
