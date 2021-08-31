package projectServlet.model.dao;

import projectServlet.model.entity.UnbanAccountRequest;

import java.util.List;

public interface UnbanAccountRequestDao extends GenericDao<UnbanAccountRequest>{
    List<UnbanAccountRequest> findByResolved(boolean resolved);
}
