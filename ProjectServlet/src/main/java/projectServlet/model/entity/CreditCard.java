package projectServlet.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */

public class CreditCard {
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private User user;
    private Account account;

    public CreditCard(Long id, String cardNumber, LocalDate expirationDate, User user, Account account) {
        this.id = id;
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

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return this.id;
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

    public void setId(Long id) {
        this.id = id;
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
        private Long id;
        private String cardNumber;
        private LocalDate expirationDate;
        private User user;
        private Account account;

        CreditCardBuilder() {
        }

        public CreditCardBuilder id(Long id) {
            this.id = id;
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
            return new CreditCard(id, cardNumber, expirationDate, user, account);
        }

        public String toString() {
            return "CreditCard.CreditCardBuilder(id=" + this.id + ", cardNumber=" + this.cardNumber + ", expirationDate=" + this.expirationDate + ", user=" + this.user + ", account=" + this.account + ")";
        }
    }
}