package projectServlet.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */

public class UnbanAccountRequest {
    private Long unban_account_request_id;
    private LocalDateTime dateTime;
    private boolean resolved;
    private Account account;

    public UnbanAccountRequest(Long id, LocalDateTime dateTime, boolean resolved, Account account) {
        this.unban_account_request_id = id;
        this.dateTime = dateTime;
        this.resolved = resolved;
        this.account = account;
    }

    public UnbanAccountRequest() {
    }

    public static UnbanAccountRequestBuilder builder() {
        return new UnbanAccountRequestBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnbanAccountRequest that = (UnbanAccountRequest) o;

        return Objects.equals(unban_account_request_id, that.unban_account_request_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unban_account_request_id);
    }

    public Long getId() {
        return this.unban_account_request_id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setId(Long unban_account_request_id) {
        this.unban_account_request_id = unban_account_request_id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        return "UnbanAccountRequest(id=" + this.getId() + ", dateTime=" + this.getDateTime() + ", resolved=" + this.isResolved() + ", account=" + this.getAccount() + ")";
    }

    public static class UnbanAccountRequestBuilder {
        private Long unban_account_request_id;
        private LocalDateTime dateTime;
        private boolean resolved;
        private Account account;

        UnbanAccountRequestBuilder() {
        }

        public UnbanAccountRequestBuilder id(Long id) {
            this.unban_account_request_id = id;
            return this;
        }

        public UnbanAccountRequestBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public UnbanAccountRequestBuilder resolved(boolean resolved) {
            this.resolved = resolved;
            return this;
        }

        public UnbanAccountRequestBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public UnbanAccountRequest build() {
            return new UnbanAccountRequest(unban_account_request_id, dateTime, resolved, account);
        }

        public String toString() {
            return "UnbanAccountRequest.UnbanAccountRequestBuilder(id=" + this.unban_account_request_id + ", dateTime=" + this.dateTime + ", resolved=" + this.resolved + ", account=" + this.account + ")";
        }
    }
}
