package projectServlet.model.dao;


import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    void save(User entity);
    List<User> findByRole(RoleType roleType);

    User authenticateUser(String username,String password);
}
