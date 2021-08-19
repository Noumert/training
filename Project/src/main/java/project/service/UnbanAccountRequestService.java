package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.UnbanAccountRequest;
import project.repository.UnbanAccountRequestRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UnbanAccountRequestService {
    @Autowired
    UnbanAccountRequestRepository unbanAccountRequestRepository;

    public void save(UnbanAccountRequest unbanAccountRequest){
        try {
            unbanAccountRequestRepository.save(unbanAccountRequest);
        }catch (RuntimeException e){
            throw new RuntimeException("problem with save");
        }
    }

    public List<UnbanAccountRequest> findAll() {
        return unbanAccountRequestRepository.findAll();
    }

    public List<UnbanAccountRequest> findByResolved(boolean resolved) {
        return unbanAccountRequestRepository.findByResolved(resolved);
    }

    public void setResolvedByRequest(boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequest.setResolved(resolved);
        save(unbanAccountRequest);
    }

    public Optional<UnbanAccountRequest> findById(Long requestId) throws NotFoundException {
        return unbanAccountRequestRepository.findById(requestId);
    }
}
