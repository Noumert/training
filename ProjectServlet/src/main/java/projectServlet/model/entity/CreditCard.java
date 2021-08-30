package projectServlet.model.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */

public class CreditCard {
    private Long credit_card_id;
    private String cardNumber;
    private LocalDate expirationDate;
    private User user;
    private Account account;

    public CreditCard(Long id, String cardNumber, LocalDate expirationDate, User user, Account account) {
        this.credit_card_id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.user = user;
        this.account = account;
    }

    public CreditCard() {
    }

    public static CreditCardBuilder builder() {
        return new CreditCardBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;

        return Objects.equals(credit_card_id, that.credit_card_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(credit_card_id);
    }

    public Long getId() {
        return this.credit_card_id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public User getUser() {
        return this.user;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setId(Long credit_card_id) {
        this.credit_card_id = credit_card_id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        return "CreditCard(id=" + this.getId() + ", cardNumber=" + this.getCardNumber() + ", expirationDate=" + this.getExpirationDate() + ", user=" + this.getUser() + ", account=" + this.getAccount() + ")";
    }

    public static class CreditCardBuilder {
        private Long credit_card_id;
        private String cardNumber;
        private LocalDate expirationDate;
        private User user;
        private Account account;

        CreditCardBuilder() {
        }

        public CreditCardBuilder id(Long id) {
            this.credit_card_id = id;
            return this;
        }

        public CreditCardBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CreditCardBuilder expirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public CreditCardBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CreditCardBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(credit_card_id, cardNumber, expirationDate, user, account);
        }

        public String toString() {
            return "CreditCard.CreditCardBuilder(id=" + this.credit_card_id + ", cardNumber=" + this.cardNumber + ", expirationDate=" + this.expirationDate + ", user=" + this.user + ", account=" + this.account + ")";
        }
    }
}