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

@Service
public class UnbanAccountRequestServiceImpl implements  UnbanAccountRequestService{
    @Autowired
    UnbanAccountRequestRepository unbanAccountRequestRepository;
    @Autowired
    AccountServiceImpl accountService;

    public void save(UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequestRepository.save(unbanAccountRequest);
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

    public Optional<UnbanAccountRequest> findById(Long requestId){
        return unbanAccountRequestRepository.findById(requestId);
    }

    @Transactional
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest){
        setResolvedByRequest(resolved, unbanAccountRequest);
        accountService.setBanByAccount(ban, unbanAccountRequest.getAccount());
    }
}
