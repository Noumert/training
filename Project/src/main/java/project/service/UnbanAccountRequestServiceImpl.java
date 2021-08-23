package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.UnbanAccountRequest;
import project.repository.UnbanAccountRequestRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class UnbanAccountRequestServiceImpl implements  UnbanAccountRequestService{
    @Autowired
    UnbanAccountRequestRepository unbanAccountRequestRepository;

    @Override
    public UnbanAccountRequest save(UnbanAccountRequest unbanAccountRequest) {
        return unbanAccountRequestRepository.save(unbanAccountRequest);
    }

    @Override
    public List<UnbanAccountRequest> findAll() {
        return unbanAccountRequestRepository.findAll();
    }

    @Override
    public List<UnbanAccountRequest> findByResolved(boolean resolved) {
        return unbanAccountRequestRepository.findByResolved(resolved);
    }

    @Override
    public UnbanAccountRequest setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequest.setResolved(resolved);
        return save(unbanAccountRequest);
    }

    @Override
    public Optional<UnbanAccountRequest> findById(Long requestId){
        return unbanAccountRequestRepository.findById(requestId);
    }
}
