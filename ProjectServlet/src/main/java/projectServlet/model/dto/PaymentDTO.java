package projectServlet.model.dto;

/**
 * Created by Noumert on 13.08.2021.
 */
public class PaymentDTO {
    private Long id;
    private String paymentNumber;
    private String money;
    private String dateTime;
    private String status;
    private AccountDTO account;
    private String recipient;

    public PaymentDTO(Long id, String paymentNumber, String money, String dateTime, String status, AccountDTO account, String recipient) {
        this.id = id;
        this.paymentNumber = paymentNumber;
        this.money = money;
        this.dateTime = dateTime;
        this.status = status;
        this.account = account;
        this.recipient = recipient;
    }

    public PaymentDTO() {
    }

    public static PaymentDTOBuilder builder() {
        return new PaymentDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getPaymentNumber() {
        return this.paymentNumber;
    }

    public String getMoney() {
        return this.money;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public String getStatus() {
        return this.status;
    }

    public AccountDTO getAccount() {
        return this.account;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PaymentDTO)) return false;
        final PaymentDTO other = (PaymentDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$paymentNumber = this.getPaymentNumber();
        final Object other$paymentNumber = other.getPaymentNumber();
        if (this$paymentNumber == null ? other$paymentNumber != null : !this$paymentNumber.equals(other$paymentNumber))
            return false;
        final Object this$money = this.getMoney();
        final Object other$money = other.getMoney();
        if (this$money == null ? other$money != null : !this$money.equals(other$money)) return false;
        final Object this$dateTime = this.getDateTime();
        final Object other$dateTime = other.getDateTime();
        if (this$dateTime == null ? other$dateTime != null : !this$dateTime.equals(other$dateTime)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$account = this.getAccount();
        final Object other$account = other.getAccount();
        if (this$account == null ? other$account != null : !this$account.equals(other$account)) return false;
        final Object this$recipient = this.getRecipient();
        final Object other$recipient = other.getRecipient();
        if (this$recipient == null ? other$recipient != null : !this$recipient.equals(other$recipient)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PaymentDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $paymentNumber = this.getPaymentNumber();
        result = result * PRIME + ($paymentNumber == null ? 43 : $paymentNumber.hashCode());
        final Object $money = this.getMoney();
        result = result * PRIME + ($money == null ? 43 : $money.hashCode());
        final Object $dateTime = this.getDateTime();
        result = result * PRIME + ($dateTime == null ? 43 : $dateTime.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $account = this.getAccount();
        result = result * PRIME + ($account == null ? 43 : $account.hashCode());
        final Object $recipient = this.getRecipient();
        result = result * PRIME + ($recipient == null ? 43 : $recipient.hashCode());
        return result;
    }

    public String toString() {
        return "PaymentDTO(id=" + this.getId() + ", paymentNumber=" + this.getPaymentNumber() + ", money=" + this.getMoney() + ", dateTime=" + this.getDateTime() + ", status=" + this.getStatus() + ", account=" + this.getAccount() + ", recipient=" + this.getRecipient() + ")";
    }

    public static class PaymentDTOBuilder {
        private Long id;
        private String paymentNumber;
        private String money;
        private String dateTime;
        private String status;
        private AccountDTO account;
        private String recipient;

        PaymentDTOBuilder() {
        }

        public PaymentDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PaymentDTOBuilder paymentNumber(String paymentNumber) {
            this.paymentNumber = paymentNumber;
            return this;
        }

        public PaymentDTOBuilder money(String money) {
            this.money = money;
            return this;
        }

        public PaymentDTOBuilder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public PaymentDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public PaymentDTOBuilder account(AccountDTO account) {
            this.account = account;
            return this;
        }

        public PaymentDTOBuilder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public PaymentDTO build() {
            return new PaymentDTO(id, paymentNumber, money, dateTime, status, account, recipient);
        }

        public String toString() {
            return "PaymentDTO.PaymentDTOBuilder(id=" + this.id + ", paymentNumber=" + this.paymentNumber + ", money=" + this.money + ", dateTime=" + this.dateTime + ", status=" + this.status + ", account=" + this.account + ", recipient=" + this.recipient + ")";
        }
    }
}
