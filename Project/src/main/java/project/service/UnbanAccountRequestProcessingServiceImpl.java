package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.entity.UnbanAccountRequest;

/**
 * Created by Noumert on 21.08.2021.
 */
@Service
public class UnbanAccountRequestProcessingServiceImpl implements UnbanAccountRequestProcessingService {
    @Autowired
    UnbanAccountRequestService unbanAccountRequestService;
    @Autowired
    AccountService accountService;

    @Override
    @Transactional
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequestService.setResolvedByRequest(resolved, unbanAccountRequest);
        accountService.setBanByAccount(ban, unbanAccountRequest.getAccount());
    }
}
