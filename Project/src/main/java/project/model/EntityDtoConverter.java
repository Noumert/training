package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.model.dto.AccountDTO;
import project.model.dto.CreditCardDto;
import project.model.dto.UserAccountDTO;
import project.model.dto.UserDTO;
import project.model.entity.Account;
import project.model.entity.CreditCard;
import project.model.entity.RoleType;
import project.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AccountDTO> convertFromAccountsListToDto(List<Account> currentUserAccounts) {
        return currentUserAccounts.stream()
                .map(account->AccountDTO.builder()
                        .id(account.getId())
                        .accountName(account.getAccountName())
                        .accountNumber(account.getAccountNumber())
                        .ban(account.isBan())
                        .money(account.getMoney())
                        .build()).collect(Collectors.toList());
    }

    public List<CreditCardDto> convertFromCardsListToDto(List<CreditCard> currentUserCards) {
        return currentUserCards.stream()
                .map(card->CreditCardDto.builder()
                        .id(card.getId())
                        .expirationDate(card.getExpirationDate())
                        .cardNumber(card.getCardNumber())
                        .money(card.getAccount().getMoney())
                        .build()).collect(Collectors.toList());
    }

    public User convertUserDtoToUser(UserDTO userDto) {
        return User
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .accountNonLocked(true)
                .role(RoleType.ROLE_USER)
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
}
