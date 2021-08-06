package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.model.dto.*;
import project.model.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AccountDTO> convertAccountsListToDTO(List<Account> currentUserAccounts) {
        return currentUserAccounts.stream()
                .map(this::convertAccountsToAccountDTO).collect(Collectors.toList());
    }

    public AccountDTO convertAccountsToAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .ban(account.isBan())
                .money(account.getMoney())
                .build();
    }

    public List<CreditCardDTO> convertCardsListToDTO(List<CreditCard> currentUserCards) {
        return currentUserCards.stream()
                .map(this::convertCreditCardsToCreditCardsDTO).collect(Collectors.toList());
    }

    public CreditCardDTO convertCreditCardsToCreditCardsDTO(CreditCard currentUserCards) {
        return CreditCardDTO.builder()
                .id(currentUserCards.getId())
                .expirationDate(currentUserCards.getExpirationDate())
                .cardNumber(currentUserCards.getCardNumber())
                .build();
    }

    public UnbanAccountRequest convertUnbanAccountRequestDTOToUnbanAccountRequest(UnbanAccountRequestDTO unbanAccountRequestDTO) {
        return UnbanAccountRequest
                .builder()
                .id(unbanAccountRequestDTO.getId())
                .dateTime(unbanAccountRequestDTO.getDateTime())
                .resolved(unbanAccountRequestDTO.isResolved())
                .account(convertAccountDTOToAccount(unbanAccountRequestDTO.getAccount()))
                .build();
    }

    public UnbanAccountRequestDTO convertUnbanAccountRequestToUnbanAccountRequestDTO(UnbanAccountRequest unbanAccountRequest) {
        return UnbanAccountRequestDTO
                .builder()
                .id(unbanAccountRequest.getId())
                .dateTime(unbanAccountRequest.getDateTime())
                .resolved(unbanAccountRequest.isResolved())
                .account(convertAccountToAccountDto(unbanAccountRequest.getAccount()))
                .build();
    }

    private Account convertAccountDTOToAccount(AccountDTO accountDTO) {
        return Account
                .builder()
                .id(accountDTO.getId())
                .ban(accountDTO.isBan())
                .accountName(accountDTO.getAccountName())
                .accountNumber(accountDTO.getAccountNumber())
                .money(accountDTO.getMoney())
                .build();
    }

    private AccountDTO convertAccountToAccountDto(Account account) {
        return AccountDTO
                .builder()
                .id(account.getId())
                .ban(account.isBan())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .money(account.getMoney())
                .build();
    }

    public List<UnbanAccountRequest> convertUnbanAccountRequestDTOsToUnbanAccountRequests(List<UnbanAccountRequestDTO>
                                                                                                 unbanAccountRequestDTOs) {
        return unbanAccountRequestDTOs.stream()
                .map(this::convertUnbanAccountRequestDTOToUnbanAccountRequest)
                .collect(Collectors.toList());

    }

    public List<UnbanAccountRequestDTO> convertUnbanAccountRequestsToUnbanAccountRequestDTOs(List<UnbanAccountRequest>
                                                                                                    unbanAccountRequests) {
        return unbanAccountRequests.stream()
                .map(this::convertUnbanAccountRequestToUnbanAccountRequestDTO)
                .collect(Collectors.toList());
    }

    public User convertUserDTOToUser(UserDTO userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .accountNonLocked(userDto.isAccountNonLocked())
                .build();
    }


    public UserDTO convertUserToUserDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }

    public List<UserAccountDTO> convertAccountsToDto(List<Account> accounts) {
        return accounts.stream()
                .map(account -> {
                    User user = account.getUser();
                    return UserAccountDTO
                            .builder()
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .accountNumber(account.getAccountNumber())
                            .accountName(account.getAccountName())
                            .money(account.getMoney())
                            .id(account.getId())
                            .ban(account.isBan())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<UserDTO> convertUserListToUserDto(List<User> users) {
        return users.stream().map(this::convertUserToUserDTO).collect(Collectors.toList());
    }
}
