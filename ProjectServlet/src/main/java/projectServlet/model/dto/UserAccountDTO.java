package projectServlet.model.dto;


/**
 * Created by Noumert on 13.08.2021.
 */
public class UserAccountDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Long id;
    private String accountNumber;
    private String accountName;
    private boolean ban;
    private String money;

    public UserAccountDTO(String email, String firstName, String lastName, Long id, String accountNumber, String accountName, boolean ban, String money) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.ban = ban;
        this.money = money;
    }

    public UserAccountDTO() {
    }

    public static UserAccountDTOBuilder builder() {
        return new UserAccountDTOBuilder();
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
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

    public boolean isBan() {
        return this.ban;
    }

    public String getMoney() {
        return this.money;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserAccountDTO)) return false;
        final UserAccountDTO other = (UserAccountDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$accountNumber = this.getAccountNumber();
        final Object other$accountNumber = other.getAccountNumber();
        if (this$accountNumber == null ? other$accountNumber != null : !this$accountNumber.equals(other$accountNumber))
            return false;
        final Object this$accountName = this.getAccountName();
        final Object other$accountName = other.getAccountName();
        if (this$accountName == null ? other$accountName != null : !this$accountName.equals(other$accountName))
            return false;
        if (this.isBan() != other.isBan()) return false;
        final Object this$money = this.getMoney();
        final Object other$money = other.getMoney();
        if (this$money == null ? other$money != null : !this$money.equals(other$money)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAccountDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $accountNumber = this.getAccountNumber();
        result = result * PRIME + ($accountNumber == null ? 43 : $accountNumber.hashCode());
        final Object $accountName = this.getAccountName();
        result = result * PRIME + ($accountName == null ? 43 : $accountName.hashCode());
        result = result * PRIME + (this.isBan() ? 79 : 97);
        final Object $money = this.getMoney();
        result = result * PRIME + ($money == null ? 43 : $money.hashCode());
        return result;
    }

    public String toString() {
        return "UserAccountDTO(email=" + this.getEmail() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", id=" + this.getId() + ", accountNumber=" + this.getAccountNumber() + ", accountName=" + this.getAccountName() + ", ban=" + this.isBan() + ", money=" + this.getMoney() + ")";
    }

    public static class UserAccountDTOBuilder {
        private String email;
        private String firstName;
        private String lastName;
        private Long id;
        private String accountNumber;
        private String accountName;
        private boolean ban;
        private String money;

        UserAccountDTOBuilder() {
        }

        public UserAccountDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserAccountDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserAccountDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserAccountDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserAccountDTOBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public UserAccountDTOBuilder accountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public UserAccountDTOBuilder ban(boolean ban) {
            this.ban = ban;
            return this;
        }

        public UserAccountDTOBuilder money(String money) {
            this.money = money;
            return this;
        }

        public UserAccountDTO build() {
            return new UserAccountDTO(email, firstName, lastName, id, accountNumber, accountName, ban, money);
        }

        public String toString() {
            return "UserAccountDTO.UserAccountDTOBuilder(email=" + this.email + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", id=" + this.id + ", accountNumber=" + this.accountNumber + ", accountName=" + this.accountName + ", ban=" + this.ban + ", money=" + this.money + ")";
        }
    }
}
