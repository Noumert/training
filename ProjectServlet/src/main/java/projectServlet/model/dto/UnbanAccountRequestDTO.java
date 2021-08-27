package projectServlet.model.dto;

/**
 * Created by Noumert on 13.08.2021.
 */
public class UnbanAccountRequestDTO {
    private Long id;
    private String dateTime;
    private boolean resolved;
    private AccountDTO account;

    public UnbanAccountRequestDTO(Long id, String dateTime, boolean resolved, AccountDTO account) {
        this.id = id;
        this.dateTime = dateTime;
        this.resolved = resolved;
        this.account = account;
    }

    public UnbanAccountRequestDTO() {
    }

    public static UnbanAccountRequestDTOBuilder builder() {
        return new UnbanAccountRequestDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public AccountDTO getAccount() {
        return this.account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UnbanAccountRequestDTO)) return false;
        final UnbanAccountRequestDTO other = (UnbanAccountRequestDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$dateTime = this.getDateTime();
        final Object other$dateTime = other.getDateTime();
        if (this$dateTime == null ? other$dateTime != null : !this$dateTime.equals(other$dateTime)) return false;
        if (this.isResolved() != other.isResolved()) return false;
        final Object this$account = this.getAccount();
        final Object other$account = other.getAccount();
        if (this$account == null ? other$account != null : !this$account.equals(other$account)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UnbanAccountRequestDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $dateTime = this.getDateTime();
        result = result * PRIME + ($dateTime == null ? 43 : $dateTime.hashCode());
        result = result * PRIME + (this.isResolved() ? 79 : 97);
        final Object $account = this.getAccount();
        result = result * PRIME + ($account == null ? 43 : $account.hashCode());
        return result;
    }

    public String toString() {
        return "UnbanAccountRequestDTO(id=" + this.getId() + ", dateTime=" + this.getDateTime() + ", resolved=" + this.isResolved() + ", account=" + this.getAccount() + ")";
    }

    public static class UnbanAccountRequestDTOBuilder {
        private Long id;
        private String dateTime;
        private boolean resolved;
        private AccountDTO account;

        UnbanAccountRequestDTOBuilder() {
        }

        public UnbanAccountRequestDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UnbanAccountRequestDTOBuilder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public UnbanAccountRequestDTOBuilder resolved(boolean resolved) {
            this.resolved = resolved;
            return this;
        }

        public UnbanAccountRequestDTOBuilder account(AccountDTO account) {
            this.account = account;
            return this;
        }

        public UnbanAccountRequestDTO build() {
            return new UnbanAccountRequestDTO(id, dateTime, resolved, account);
        }

        public String toString() {
            return "UnbanAccountRequestDTO.UnbanAccountRequestDTOBuilder(id=" + this.id + ", dateTime=" + this.dateTime + ", resolved=" + this.resolved + ", account=" + this.account + ")";
        }
    }
}
