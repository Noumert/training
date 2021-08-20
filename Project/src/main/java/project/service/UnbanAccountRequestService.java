package project.service;

import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import project.entity.UnbanAccountRequest;

import java.util.List;
import java.util.Optional;

public interface UnbanAccountRequestService {
    public void save(UnbanAccountRequest unbanAccountRequest);

    public List<UnbanAccountRequest> findAll();

    public List<UnbanAccountRequest> findByResolved(boolean resolved);

    public void setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest);

    public Optional<UnbanAccountRequest> findById(Long requestId);

    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest);
}
