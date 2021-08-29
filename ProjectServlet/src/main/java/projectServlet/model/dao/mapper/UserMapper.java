package projectServlet.model.dao.mapper;

import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {


    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .accountNonLocked(rs.getBoolean("account_non_locked"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .role(RoleType.valueOf(rs.getString("role")))
                .build();
    }

    @Override
    public User makeUnique(Map<Long, User> cache,
                           User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
