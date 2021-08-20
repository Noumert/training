package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.*;
import project.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    private static final DateTimeFormatter formatterWithoutTime = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);
    @Autowired
    MoneyFormatConverter moneyFormatConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AccountDTO> convertAccountsListToDTO(List<Account> accounts) {
        return accounts.stream()
                .map(this::convertAccountToAccountDTO).collect(Collectors.toList());
    }

    public Page<AccountDTO> convertAccountsListToDTO(Page<Account> accounts) {
        return accounts.map(this::convertAccountToAccountDTO);
    }

    public List<Account> convertAccountDTOsListToAccounts(List<AccountDTO> accountDTOS) {
        return accountDTOS.stream()
                .map(this::convertAccountDTOToAccount).collect(Collectors.toList());
    }

    public Account convertAccountDTOToAccount(AccountDTO accountDTO) {
        return Account
                .builder()
                .id(accountDTO.getId())
                .ban(accountDTO.isBan())
                .accountName(accountDTO.getAccountName())
                .accountNumber(accountDTO.getAccountNumber())
                .money(moneyFormatConverter.getMoneyValue(accountDTO.getMoney()))
                .build();
    }

    public AccountDTO convertAccountToAccountDTO(Account account) {
        return AccountDTO
                .builder()
                .id(account.getId())
                .ban(account.isBan())
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .money(moneyFormatConverter.getStringMoneyFromMoneyValue(account.getMoney()))
                .build();
    }

    public List<CreditCardDTO> convertCardsListToDTO(List<CreditCard> currentUserCards) {
        return currentUserCards.stream()
                .map(this::convertCreditCardsToCreditCardsDTO).collect(Collectors.toList());
    }

    public CreditCardDTO convertCreditCardsToCreditCardsDTO(CreditCard currentUserCards) {
        return CreditCardDTO.builder()
                .id(currentUserCards.getId())
                .expirationDate(currentUserCards.getExpirationDate().format(formatterWithoutTime.withLocale(LocaleContextHolder.getLocale())))
                .cardNumber(currentUserCards.getCardNumber())
                .build();
    }

    public UnbanAccountRequest convertUnbanAccountRequestDTOToUnbanAccountRequest(UnbanAccountRequestDTO unbanAccountRequestDTO) {
        return UnbanAccountRequest
                .builder()
                .id(unbanAccountRequestDTO.getId())
                .dateTime(LocalDateTime.parse(unbanAccountRequestDTO.getDateTime(), formatter.withLocale(LocaleContextHolder.getLocale())))
                .resolved(unbanAccountRequestDTO.isResolved())
                .account(convertAccountDTOToAccount(unbanAccountRequestDTO.getAccount()))
                .build();
    }

    public UnbanAccountRequestDTO convertUnbanAccountRequestToUnbanAccountRequestDTO(UnbanAccountRequest unbanAccountRequest) {
        return UnbanAccountRequestDTO
                .builder()
                .id(unbanAccountRequest.getId())
                .dateTime(unbanAccountRequest.getDateTime().format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .resolved(unbanAccountRequest.isResolved())
                .account(convertAccountToAccountDTO(unbanAccountRequest.getAccount()))
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
                            .money(moneyFormatConverter.getStringMoneyFromMoneyValue(account.getMoney()))
                            .id(account.getId())
                            .ban(account.isBan())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<UserDTO> convertUserListToUserDto(List<User> users) {
        return users.stream().map(this::convertUserToUserDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> convertPaymentsListToDTO(List<Payment> payments) {
        return payments.stream()
                .map(this::convertPaymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    public Page<PaymentDTO> convertPaymentsListToDTO(Page<Payment> payments) {
        return payments.map(this::convertPaymentToPaymentDTO);
    }

    public List<Payment> convertPaymentDTOsListToPayments(List<PaymentDTO> paymentDTOS) {
        return paymentDTOS.stream()
                .map(this::convertPaymentDTOToPayment)
                .collect(Collectors.toList());
    }

    public PaymentDTO convertPaymentToPaymentDTO(Payment payment) {
        return PaymentDTO
                .builder()
                .account(convertAccountToAccountDTO(payment.getAccount()))
                .id(payment.getId())
                .dateTime(payment.getDateTime().format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .paymentNumber(payment.getPaymentNumber())
                .recipient(payment.getRecipient())
                .status(payment.getStatus().name())
                .money(moneyFormatConverter.getStringMoneyFromMoneyValue(payment.getMoney()))
                .build();
    }

    public Payment convertPaymentDTOToPayment(PaymentDTO paymentDTO) {
        return Payment
                .builder()
                .account(convertAccountDTOToAccount(paymentDTO.getAccount()))
                .id(paymentDTO.getId())
                .dateTime(LocalDateTime.parse(paymentDTO.getDateTime(), formatter.withLocale(LocaleContextHolder.getLocale())))
                .paymentNumber(paymentDTO.getPaymentNumber())
                .recipient(paymentDTO.getRecipient())
                .status(StatusType.valueOf(paymentDTO.getStatus()))
                .money(moneyFormatConverter.getMoneyValue(paymentDTO.getMoney()))
                .build();
    }
}
