package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UnbanAccountRequestDao;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.entity.User;
import projectServlet.model.service.UnbanAccountRequestServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UnbanAccountRequestServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private UnbanAccountRequestDao unbanAccountRequestDao;

    @InjectMocks
    private UnbanAccountRequestServiceImpl unbanAccountRequestService;

    @Test
    public void saveSuccess() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doNothing().when(unbanAccountRequestDao).save(any(UnbanAccountRequest.class));

        assertDoesNotThrow(()->unbanAccountRequestService.save(unbanAccountRequest));
    }

    @Test
    public void saveException() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doThrow(RuntimeException.class).when(unbanAccountRequestDao).save(any(UnbanAccountRequest.class));

        assertThrows(RuntimeException.class, () -> unbanAccountRequestService.save(unbanAccountRequest));
    }

    @Test
    void findAll() {
        UnbanAccountRequest unbanAccountRequest1 = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        UnbanAccountRequest unbanAccountRequest2 = UnbanAccountRequest.builder()
                .id(2L)
                .build();
        List<UnbanAccountRequest> unbanAccountRequests = Arrays.asList(unbanAccountRequest1, unbanAccountRequest2);
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doReturn(unbanAccountRequests).when(unbanAccountRequestDao).findAll();

        assertThat(unbanAccountRequestService.findAll()).isEqualTo(unbanAccountRequests);
    }

    @Test
    void findByResolved() {
        UnbanAccountRequest unbanAccountRequest1 = UnbanAccountRequest.builder()
                .id(1L)
                .resolved(true)
                .build();
        UnbanAccountRequest unbanAccountRequest2 = UnbanAccountRequest.builder()
                .id(2L)
                .resolved(true)
                .build();
        List<UnbanAccountRequest> unbanAccountRequests = Arrays.asList(unbanAccountRequest1, unbanAccountRequest2);
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doReturn(unbanAccountRequests).when(unbanAccountRequestDao).findByResolved(true);

        assertThat(unbanAccountRequestService.findByResolved(true)).isEqualTo(unbanAccountRequests);
    }

    @Test
    void setResolvedByRequest() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .resolved(true)
                .build();
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doNothing().when(unbanAccountRequestDao).save(any(UnbanAccountRequest.class));

        assertDoesNotThrow(()->unbanAccountRequestService.save(unbanAccountRequest));
    }

    @Test
    void findById() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUnbanAccountRequestDao()).thenReturn(unbanAccountRequestDao);
        Mockito.doReturn(Optional.of(unbanAccountRequest)).when(unbanAccountRequestDao).findById(1L);

        assertThat(unbanAccountRequestService.findById(1L)).isEqualTo(Optional.of(unbanAccountRequest));
    }
}