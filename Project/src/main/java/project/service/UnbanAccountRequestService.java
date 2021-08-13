package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.UnbanAccountRequest;
import project.repository.UnbanAccountRequestRepository;

import java.util.List;

@Slf4j
@Service
public class UnbanAccountRequestService {
    @Autowired
    UnbanAccountRequestRepository unbanAccountRequestRepository;

    public void save(UnbanAccountRequest unbanAccountRequest){
        unbanAccountRequestRepository.save(unbanAccountRequest);
    }

    public List<UnbanAccountRequest> findAll() {
        return unbanAccountRequestRepository.findAll();
    }

    public List<UnbanAccountRequest> findByResolved(boolean resolved) {
        return unbanAccountRequestRepository.findByResolved(resolved);
    }

    public void setResolvedById(boolean resolved, Long requestId) {
        unbanAccountRequestRepository.setResolvedById(resolved, requestId);
    }

    public UnbanAccountRequest findById(Long requestId) throws NotFoundException {
        return unbanAccountRequestRepository.findById(requestId).orElseThrow(()-> new NotFoundException("no such request"));
    }
}
