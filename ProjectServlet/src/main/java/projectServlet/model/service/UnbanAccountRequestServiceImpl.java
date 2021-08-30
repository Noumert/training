package projectServlet.model.service;

import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UnbanAccountRequestDao;
import projectServlet.model.dao.UserDao;
import projectServlet.model.entity.UnbanAccountRequest;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 30.08.2021.
 */
public class UnbanAccountRequestServiceImpl implements UnbanAccountRequestService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void save(UnbanAccountRequest unbanAccountRequest) {
        try (UnbanAccountRequestDao dao = daoFactory.createUnbanAccountRequestDao()) {
            dao.save(unbanAccountRequest);
        }
    }

    @Override
    public List<UnbanAccountRequest> findAll() {
        try (UnbanAccountRequestDao dao = daoFactory.createUnbanAccountRequestDao()) {
            return dao.findAll();
        }
    }

    @Override
    public List<UnbanAccountRequest> findByResolved(boolean resolved) {
        try (UnbanAccountRequestDao dao = daoFactory.createUnbanAccountRequestDao()) {
            return dao.findByResolved(resolved);
        }
    }

    @Override
    public void setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequest.setResolved(resolved);
        save(unbanAccountRequest);
    }

    @Override
    public Optional<UnbanAccountRequest> findById(Long requestId){
        try (UnbanAccountRequestDao dao = daoFactory.createUnbanAccountRequestDao()) {
            return dao.findById(requestId);
        }
    }
}
