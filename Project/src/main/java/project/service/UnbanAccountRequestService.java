package project.service;

import project.entity.UnbanAccountRequest;

import java.util.List;
import java.util.Optional;

public interface UnbanAccountRequestService {
    UnbanAccountRequest save(UnbanAccountRequest unbanAccountRequest);

    List<UnbanAccountRequest> findAll();

    List<UnbanAccountRequest> findByResolved(boolean resolved);

    UnbanAccountRequest setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest);

    Optional<UnbanAccountRequest> findById(Long requestId);
}
