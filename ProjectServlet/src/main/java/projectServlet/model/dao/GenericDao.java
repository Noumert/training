package projectServlet.model.dao;

import projectServlet.model.entity.Account;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void save(T entity);
    void create (T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    void update(T entity);
    void delete(Long id);
    void close();
}
