package projectServlet.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
public class AccountDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private String accountName;
    @NotNull
    @NotEmpty
    private String money;
    @NotNull
    @NotEmpty
    private String accountNumber;
    private boolean ban;

    public AccountDTO(@NotNull Long id, @NotNull @NotEmpty String accountName, @NotNull @NotEmpty String money, @NotNull @NotEmpty String accountNumber, boolean ban) {
        this.id = id;
        this.accountName = accountName;
        this.money = money;
        this.accountNumber = accountNumber;
        this.ban = ban;
    }

    public AccountDTO() {
    }

    public static AccountDTOBuilder builder() {
        return new AccountDTOBuilder();
    }

    public @NotNull Long getId() {
        return this.id;
    }

    public @NotNull @NotEmpty String getAccountName() {
        return this.accountName;
    }

    public @NotNull @NotEmpty String getMoney() {
        return this.money;
    }

    public @NotNull @NotEmpty String getAccountNumber() {
        return this.accountNumber;
    }

    public boolean isBan() {
        return this.ban;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public void setAccountName(@NotNull @NotEmpty String accountName) {
        this.accountName = accountName;
    }

    public void setMoney(@NotNull @NotEmpty String money) {
        this.money = money;
    }

    public void setAccountNumber(@NotNull @NotEmpty String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AccountDTO)) return false;
        final AccountDTO other = (AccountDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$accountName = this.getAccountName();
        final Object other$accountName = other.getAccountName();
        if (this$accountName == null ? other$accountName != null : !this$accountName.equals(other$accountName))
            return false;
        final Object this$money = this.getMoney();
        final Object other$money = other.getMoney();
        if (this$money == null ? other$money != null : !this$money.equals(other$money)) return false;
        final Object this$accountNumber = this.getAccountNumber();
        final Object other$accountNumber = other.getAccountNumber();
        if (this$accountNumber == null ? other$accountNumber != null : !this$accountNumber.equals(other$accountNumber))
            return false;
        if (this.isBan() != other.isBan()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AccountDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $accountName = this.getAccountName();
        result = result * PRIME + ($accountName == null ? 43 : $accountName.hashCode());
        final Object $money = this.getMoney();
        result = result * PRIME + ($money == null ? 43 : $money.hashCode());
        final Object $accountNumber = this.getAccountNumber();
        result = result * PRIME + ($accountNumber == null ? 43 : $accountNumber.hashCode());
        result = result * PRIME + (this.isBan() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "AccountDTO(id=" + this.getId() + ", accountName=" + this.getAccountName() + ", money=" + this.getMoney() + ", accountNumber=" + this.getAccountNumber() + ", ban=" + this.isBan() + ")";
    }

    public static class AccountDTOBuilder {
        private @NotNull Long id;
        private @NotNull @NotEmpty String accountName;
        private @NotNull @NotEmpty String money;
        private @NotNull @NotEmpty String accountNumber;
        private boolean ban;

        AccountDTOBuilder() {
        }

        public AccountDTOBuilder id(@NotNull Long id) {
            this.id = id;
            return this;
        }

        public AccountDTOBuilder accountName(@NotNull @NotEmpty String accountName) {
            this.accountName = accountName;
            return this;
        }

        public AccountDTOBuilder money(@NotNull @NotEmpty String money) {
            this.money = money;
            return this;
        }

        public AccountDTOBuilder accountNumber(@NotNull @NotEmpty String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountDTOBuilder ban(boolean ban) {
            this.ban = ban;
            return this;
        }

        public AccountDTO build() {
            return new AccountDTO(id, accountName, money, accountNumber, ban);
        }

        public String toString() {
            return "AccountDTO.AccountDTOBuilder(id=" + this.id + ", accountName=" + this.accountName + ", money=" + this.money + ", accountNumber=" + this.accountNumber + ", ban=" + this.ban + ")";
        }
    }
}
