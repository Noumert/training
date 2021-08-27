package projectServlet.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
public class FillPaymentDTO {
    private Long id;
    @NotNull
    private Long accountId;
    @NotNull @Min(value = 1)
    @Max(value = 99999)
    private String paymentMoney;
    @NotEmpty
    @NotNull
    private String recipient;

    public FillPaymentDTO(Long id, Long accountId, @NotNull @Min(value = 1) @Max(value = 99999) String paymentMoney, @NotEmpty @NotNull String recipient) {
        this.id = id;
        this.accountId = accountId;
        this.paymentMoney = paymentMoney;
        this.recipient = recipient;
    }

    public FillPaymentDTO() {
    }

    public static FillPaymentDTOBuilder builder() {
        return new FillPaymentDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public @NotNull @Min(value = 1) @Max(value = 99999) String getPaymentMoney() {
        return this.paymentMoney;
    }

    public @NotEmpty @NotNull String getRecipient() {
        return this.recipient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setPaymentMoney(@NotNull @Min(value = 1) @Max(value = 99999) String paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public void setRecipient(@NotEmpty @NotNull String recipient) {
        this.recipient = recipient;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FillPaymentDTO)) return false;
        final FillPaymentDTO other = (FillPaymentDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$accountId = this.getAccountId();
        final Object other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) return false;
        final Object this$paymentMoney = this.getPaymentMoney();
        final Object other$paymentMoney = other.getPaymentMoney();
        if (this$paymentMoney == null ? other$paymentMoney != null : !this$paymentMoney.equals(other$paymentMoney))
            return false;
        final Object this$recipient = this.getRecipient();
        final Object other$recipient = other.getRecipient();
        if (this$recipient == null ? other$recipient != null : !this$recipient.equals(other$recipient)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FillPaymentDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $accountId = this.getAccountId();
        result = result * PRIME + ($accountId == null ? 43 : $accountId.hashCode());
        final Object $paymentMoney = this.getPaymentMoney();
        result = result * PRIME + ($paymentMoney == null ? 43 : $paymentMoney.hashCode());
        final Object $recipient = this.getRecipient();
        result = result * PRIME + ($recipient == null ? 43 : $recipient.hashCode());
        return result;
    }

    public String toString() {
        return "FillPaymentDTO(id=" + this.getId() + ", accountId=" + this.getAccountId() + ", paymentMoney=" + this.getPaymentMoney() + ", recipient=" + this.getRecipient() + ")";
    }

    public static class FillPaymentDTOBuilder {
        private Long id;
        private Long accountId;
        private @NotNull @Min(value = 1) @Max(value = 99999) String paymentMoney;
        private @NotEmpty @NotNull String recipient;

        FillPaymentDTOBuilder() {
        }

        public FillPaymentDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FillPaymentDTOBuilder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public FillPaymentDTOBuilder paymentMoney(@NotNull @Min(value = 1) @Max(value = 99999) String paymentMoney) {
            this.paymentMoney = paymentMoney;
            return this;
        }

        public FillPaymentDTOBuilder recipient(@NotEmpty @NotNull String recipient) {
            this.recipient = recipient;
            return this;
        }

        public FillPaymentDTO build() {
            return new FillPaymentDTO(id, accountId, paymentMoney, recipient);
        }

        public String toString() {
            return "FillPaymentDTO.FillPaymentDTOBuilder(id=" + this.id + ", accountId=" + this.accountId + ", paymentMoney=" + this.paymentMoney + ", recipient=" + this.recipient + ")";
        }
    }
}
