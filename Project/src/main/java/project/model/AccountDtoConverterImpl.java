package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.entity.Account;
import project.dto.AccountDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
@Component
public class AccountDtoConverterImpl implements EntityDtoConverter<Account, AccountDTO> {
    @Autowired
    private MoneyFormatConverter moneyFormatConverter;

    @Override
    public AccountDTO convertEntityToDto(Account account) {
        return AccountDTO
                .builder()
                .id(account.getId())
                .ban(account.isBan())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .money(moneyFormatConverter.getStringMoneyFromMoneyValue(account.getMoney()))
                .build();
    }

    @Override
    public Account convertDtoToEntity(AccountDTO accountDTO) {
        return Account
                .builder()
                .id(accountDTO.getId())
                .ban(accountDTO.isBan())
                .accountName(accountDTO.getAccountName())
                .accountNumber(accountDTO.getAccountNumber())
                .money(moneyFormatConverter.getMoneyValue(accountDTO.getMoney()))
                .build();
    }

    @Override
    public List<AccountDTO> convertEntityListToDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Account> convertDtoListToEntityList(List<AccountDTO> accountDTOS) {
        return accountDTOS.stream()
                .map(this::convertDtoToEntity).collect(Collectors.toList());
    }

    @Override
    public Page<AccountDTO> convertEntityPageToDtoPage(Page<Account> accounts) {
        return accounts.map(this::convertEntityToDto);
    }

    @Override
    public Page<Account> convertDtoPageToEntityPage(Page<AccountDTO> accountDTOS) {
        return accountDTOS.map(this::convertDtoToEntity);
    }
}
