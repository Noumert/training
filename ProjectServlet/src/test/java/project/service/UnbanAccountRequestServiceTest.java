package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.UnbanAccountRequest;
import project.repository.UnbanAccountRequestRepository;

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
    private UnbanAccountRequestRepository unbanAccountRequestRepository;

    @InjectMocks
    private UnbanAccountRequestServiceImpl unbanAccountRequestService;

    @Test
    public void saveSuccess() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(unbanAccountRequestRepository.save(any(UnbanAccountRequest.class))).then(returnsFirstArg());

        assertThat(unbanAccountRequestService.save(unbanAccountRequest)).isEqualTo(unbanAccountRequest);
    }

    @Test
    public void saveException() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(unbanAccountRequestRepository.save(any(UnbanAccountRequest.class))).thenThrow(RuntimeException.class);

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
        Mockito.when(unbanAccountRequestRepository.findAll()).thenReturn(unbanAccountRequests);

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
        Mockito.when(unbanAccountRequestRepository.findByResolved(true)).thenReturn(unbanAccountRequests);

        assertThat(unbanAccountRequestService.findByResolved(true)).isEqualTo(unbanAccountRequests);
    }

    @Test
    void setResolvedByRequest() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .resolved(true)
                .build();
        Mockito.when(unbanAccountRequestRepository.save(any(UnbanAccountRequest.class))).then(returnsFirstArg());

        assertThat(unbanAccountRequestService.setResolvedByRequest(true,unbanAccountRequest).isResolved()).isEqualTo(true);
    }

    @Test
    void findById() {
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest.builder()
                .id(1L)
                .build();
        Mockito.when(unbanAccountRequestRepository.findById(1L)).thenReturn(Optional.of(unbanAccountRequest));

        assertThat(unbanAccountRequestService.findById(1L)).isEqualTo(Optional.of(unbanAccountRequest));
    }
}