package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public void save(UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequestRepository.save(unbanAccountRequest);
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
    public void setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequest.setResolved(resolved);
        save(unbanAccountRequest);
    }

    @Override
    public Optional<UnbanAccountRequest> findById(Long requestId){
        return unbanAccountRequestRepository.findById(requestId);
    }
}
