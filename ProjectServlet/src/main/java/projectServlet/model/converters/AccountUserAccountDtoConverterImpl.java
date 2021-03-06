package projectServlet.model.converters;

import projectServlet.model.dto.UserAccountDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */

public class AccountUserAccountDtoConverterImpl implements EntityDtoConverter<Account, UserAccountDTO> {
    MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();

    @Override
    public UserAccountDTO convertEntityToDto(Account account) {
        User user = account.getUser();
        return UserAccountDTO
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .money(moneyFormatConverter.getStringMoneyFromMoneyValue(account.getMoney()))
                .id(account.getId())
                .ban(account.isBan())
                .build();
    }

    @Override
    public Account convertDtoToEntity(UserAccountDTO userAccountDTO) {
        return Account
                .builder()
                .accountNumber(userAccountDTO.getAccountNumber())
                .accountName(userAccountDTO.getAccountName())
                .money(moneyFormatConverter.getMoneyValue(userAccountDTO.getMoney()))
                .id(userAccountDTO.getId())
                .ban(userAccountDTO.isBan())
                .build();
    }

    @Override
    public List<UserAccountDTO> convertEntityListToDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> convertDtoListToEntityList(List<UserAccountDTO> userAccountDTOS) {
        return userAccountDTOS.stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public Page<UserAccountDTO> convertEntityPageToDtoPage(Page<Account> accounts) {
//        return null;
//    }
//
//    @Override
//    public Page<Account> convertDtoPageToEntityPage(Page<UserAccountDTO> userAccountDTOS) {
//        return null;
//    }
}
