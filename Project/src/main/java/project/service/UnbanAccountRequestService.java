package project.service;

import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import project.entity.UnbanAccountRequest;

import java.util.List;
import java.util.Optional;

public interface UnbanAccountRequestService {
    void save(UnbanAccountRequest unbanAccountRequest);

    List<UnbanAccountRequest> findAll();

    List<UnbanAccountRequest> findByResolved(boolean resolved);

    void setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest);

    Optional<UnbanAccountRequest> findById(Long requestId);
}
