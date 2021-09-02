package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.Account;
import project.entity.UnbanAccountRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnbanAccountRequestProcessingServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private UnbanAccountRequestService unbanAccountRequestService;

    @InjectMocks
    private UnbanAccountRequestProcessingServiceImpl unbanAccountRequestProcessingService;

    @Test
    public void unbanAndSetResolvedByRequest() {
        Account account = Account.builder()
                .id(1L)
                .ban(true)
                .build();
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .account(account)
                .build();
        Mockito.when(accountService.setBanByAccount(true,unbanAccountRequest.getAccount())).thenReturn(account);
        Mockito.when(unbanAccountRequestService.setResolvedByRequest(true,unbanAccountRequest))
                .thenReturn(unbanAccountRequest);

        assertDoesNotThrow(()->unbanAccountRequestProcessingService
                .unbanAndSetResolvedByRequest(true,true,unbanAccountRequest));
        verify(accountService,only()).setBanByAccount(true,unbanAccountRequest.getAccount());
        verify(unbanAccountRequestService,only()).setResolvedByRequest(true,unbanAccountRequest);
    }
}