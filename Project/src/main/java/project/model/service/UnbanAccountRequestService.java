package project.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entity.UnbanAccountRequest;
import project.model.repository.UnbanAccountRequestRepository;

@Service
public class UnbanAccountRequestService {
    @Autowired
    UnbanAccountRequestRepository unbanAccountRequestRepository;

    public void save(UnbanAccountRequest unbanAccountRequest){
        unbanAccountRequestRepository.save(unbanAccountRequest);
    }
}
