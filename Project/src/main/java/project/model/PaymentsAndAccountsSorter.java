package project.model;

import org.springframework.stereotype.Component;
import project.model.entity.Account;
import project.model.entity.Payment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentsAndAccountsSorter{
    public List<Account> sortAccountsByName(List<Account> accounts){
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getAccountName))
                .collect(Collectors.toList());
    }

    public List<Account> sortAccountsByNumber(List<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    public List<Account> sortAccountsByMoney(List<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getMoney))
                .collect(Collectors.toList());
    }

    public List<Payment> sortPaymentsByNumber(List<Payment> payments) {
        return payments.stream()
                .sorted(Comparator.comparing(Payment::getPaymentNumber))
                .collect(Collectors.toList());
    }

    public List<Payment> sortPaymentsByDataASC(List<Payment> payments) {
        return payments.stream()
                .sorted(Comparator.comparing(Payment::getDateTime))
                .collect(Collectors.toList());
    }

    public List<Payment> sortPaymentsByDataDESC(List<Payment> payments) {
        return payments.stream()
                .sorted(Comparator.comparing(Payment::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
