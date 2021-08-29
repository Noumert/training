package projectServlet.model.converters;



import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.UnbanAccountRequestDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.UnbanAccountRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
public class UnbanAccountRequestDtoConverterImpl implements
        EntityDtoConverter<UnbanAccountRequest, UnbanAccountRequestDTO> {
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    private EntityDtoConverter<Account, AccountDTO> accountDtoConverter = new AccountDtoConverterImpl();

    @Override
    public UnbanAccountRequestDTO convertEntityToDto(UnbanAccountRequest unbanAccountRequest) {
        return UnbanAccountRequestDTO
                .builder()
                .id(unbanAccountRequest.getId())
                .dateTime(unbanAccountRequest.getDateTime().format(formatter.withLocale(Locale.getDefault())))
                .resolved(unbanAccountRequest.isResolved())
                .account(accountDtoConverter.convertEntityToDto(unbanAccountRequest.getAccount()))
                .build();
    }

    @Override
    public UnbanAccountRequest convertDtoToEntity(UnbanAccountRequestDTO unbanAccountRequestDTO) {
        return UnbanAccountRequest
                .builder()
                .id(unbanAccountRequestDTO.getId())
                .dateTime(LocalDateTime.parse(unbanAccountRequestDTO.getDateTime(), formatter.withLocale(Locale.getDefault())))
                .resolved(unbanAccountRequestDTO.isResolved())
                .account(accountDtoConverter.convertDtoToEntity(unbanAccountRequestDTO.getAccount()))
                .build();
    }

    @Override
    public List<UnbanAccountRequestDTO> convertEntityListToDtoList(List<UnbanAccountRequest> unbanAccountRequests) {
        return unbanAccountRequests.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UnbanAccountRequest> convertDtoListToEntityList(List<UnbanAccountRequestDTO> unbanAccountRequestDTOS) {
        return unbanAccountRequestDTOS.stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public Page<UnbanAccountRequestDTO> convertEntityPageToDtoPage(Page<UnbanAccountRequest> unbanAccountRequests) {
//        return unbanAccountRequests.map(this::convertEntityToDto);
//    }
//
//    @Override
//    public Page<UnbanAccountRequest> convertDtoPageToEntityPage(Page<UnbanAccountRequestDTO> unbanAccountRequestDTOS) {
//        return unbanAccountRequestDTOS.map(this::convertDtoToEntity);
//    }
}
