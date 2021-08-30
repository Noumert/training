package projectServlet.model.service;


import projectServlet.model.entity.UnbanAccountRequest;

/**
 * Created by Noumert on 21.08.2021.
 */
public class UnbanAccountRequestProcessingServiceImpl implements UnbanAccountRequestProcessingService {
    private UnbanAccountRequestService unbanAccountRequestService = new UnbanAccountRequestServiceImpl();
    private AccountService accountService = new AccountServiceImpl();

    @Override
//    @Transactional
    //TODO transactional
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest) {
        unbanAccountRequestService.setResolvedByRequest(resolved, unbanAccountRequest);
        accountService.setBanByAccount(ban, unbanAccountRequest.getAccount());
    }
}
