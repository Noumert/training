package projectServlet.model.entity;

import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */
public class Account {

    private Long id;
    private String accountNumber;
    private String accountName;
    private Long money;
    private boolean ban;
    private User user;

    public Account(Long id, String accountNumber, String accountName, Long money, boolean ban, User user) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.money = money;
        this.ban = ban;
        this.user = user;
    }

    public Account() {
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;

        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return this.id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public Long getMoney() {
        return this.money;
    }

    public boolean isBan() {
        return this.ban;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "Account(id=" + this.getId() + ", accountNumber=" + this.getAccountNumber() + ", accountName=" + this.getAccountName() + ", money=" + this.getMoney() + ", ban=" + this.isBan() + ", user=" + this.getUser() + ")";
    }

    public static class AccountBuilder {
        private Long id;
        private String accountNumber;
        private String accountName;
        private Long money;
        private boolean ban;
        private User user;

        AccountBuilder() {
        }

        public AccountBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AccountBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountBuilder accountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public AccountBuilder money(Long money) {
            this.money = money;
            return this;
        }

        public AccountBuilder ban(boolean ban) {
            this.ban = ban;
            return this;
        }

        public AccountBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(id, accountNumber, accountName, money, ban, user);
        }

        public String toString() {
            return "Account.AccountBuilder(id=" + this.id + ", accountNumber=" + this.accountNumber + ", accountName=" + this.accountName + ", money=" + this.money + ", ban=" + this.ban + ", user=" + this.user + ")";
        }
    }
}
