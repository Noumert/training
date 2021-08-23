package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.dto.UnbanAccountRequestDTO;
import project.entity.Account;
import project.entity.UnbanAccountRequest;
import project.dto.AccountDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
@Component
public class UnbanAccountRequestDtoConverterImpl implements
        EntityDtoConverter<UnbanAccountRequest, UnbanAccountRequestDTO> {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    @Autowired
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter;

    @Override
    public UnbanAccountRequestDTO convertEntityToDto(UnbanAccountRequest unbanAccountRequest) {
        return UnbanAccountRequestDTO
                .builder()
                .id(unbanAccountRequest.getId())
                .dateTime(unbanAccountRequest.getDateTime().format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .resolved(unbanAccountRequest.isResolved())
                .account(accountDtoConverter.convertEntityToDto(unbanAccountRequest.getAccount()))
                .build();
    }

    @Override
    public UnbanAccountRequest convertDtoToEntity(UnbanAccountRequestDTO unbanAccountRequestDTO) {
        return UnbanAccountRequest
                .builder()
                .id(unbanAccountRequestDTO.getId())
                .dateTime(LocalDateTime.parse(unbanAccountRequestDTO.getDateTime(), formatter.withLocale(LocaleContextHolder.getLocale())))
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

    @Override
    public Page<UnbanAccountRequestDTO> convertEntityPageToDtoPage(Page<UnbanAccountRequest> unbanAccountRequests) {
        return unbanAccountRequests.map(this::convertEntityToDto);
    }

    @Override
    public Page<UnbanAccountRequest> convertDtoPageToEntityPage(Page<UnbanAccountRequestDTO> unbanAccountRequestDTOS) {
        return unbanAccountRequestDTOS.map(this::convertDtoToEntity);
    }
}
