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

    public List<AccountDTO> convertAccountsListToDto(List<Account> currentUserAccounts) {
        return currentUserAccounts.stream()
                .map(this::convertAccountsToAccountDto).collect(Collectors.toList());
    }

    public AccountDTO convertAccountsToAccountDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .ban(account.isBan())
                .money(account.getMoney())
                .build();
    }

    public List<CreditCardDTO> convertCardsListToDto(List<CreditCard> currentUserCards) {
        return currentUserCards.stream()
                .map(this::convertCreditCardsToCreditCardsDto).collect(Collectors.toList());
    }

    public CreditCardDTO convertCreditCardsToCreditCardsDto(CreditCard currentUserCards) {
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
                .account(unbanAccountRequestDTO.getAccount())
                .build();
    }

    public UnbanAccountRequestDTO convertUnbanAccountRequestDTOToUser(UnbanAccountRequest unbanAccountRequest) {
        return UnbanAccountRequestDTO
                .builder()
                .id(unbanAccountRequest.getId())
                .dateTime(unbanAccountRequest.getDateTime())
                .resolved(unbanAccountRequest.isResolved())
                .account(unbanAccountRequest.getAccount())
                .build();
    }

    public User convertUserDtoToUser(UserDTO userDto) {
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



    public UserDTO convertUserToUserDto(User user) {
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
        return users.stream().map(this::convertUserToUserDto).collect(Collectors.toList());
    }
}
