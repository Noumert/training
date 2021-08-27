package projectServlet.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
public class TopUpDTO {
    Long accountId;
    @NotNull @Min(value = 1)
    @Max(value = 99999)
    Double topUpMoney;

    public TopUpDTO(Long accountId, @NotNull @Min(value = 1) @Max(value = 99999) Double topUpMoney) {
        this.accountId = accountId;
        this.topUpMoney = topUpMoney;
    }

    public TopUpDTO() {
    }

    public static TopUpDTOBuilder builder() {
        return new TopUpDTOBuilder();
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public @NotNull @Min(value = 1) @Max(value = 99999) Double getTopUpMoney() {
        return this.topUpMoney;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setTopUpMoney(@NotNull @Min(value = 1) @Max(value = 99999) Double topUpMoney) {
        this.topUpMoney = topUpMoney;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TopUpDTO)) return false;
        final TopUpDTO other = (TopUpDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$accountId = this.getAccountId();
        final Object other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) return false;
        final Object this$topUpMoney = this.getTopUpMoney();
        final Object other$topUpMoney = other.getTopUpMoney();
        if (this$topUpMoney == null ? other$topUpMoney != null : !this$topUpMoney.equals(other$topUpMoney))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TopUpDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $accountId = this.getAccountId();
        result = result * PRIME + ($accountId == null ? 43 : $accountId.hashCode());
        final Object $topUpMoney = this.getTopUpMoney();
        result = result * PRIME + ($topUpMoney == null ? 43 : $topUpMoney.hashCode());
        return result;
    }

    public String toString() {
        return "TopUpDTO(accountId=" + this.getAccountId() + ", topUpMoney=" + this.getTopUpMoney() + ")";
    }

    public static class TopUpDTOBuilder {
        private Long accountId;
        private @NotNull @Min(value = 1) @Max(value = 99999) Double topUpMoney;

        TopUpDTOBuilder() {
        }

        public TopUpDTOBuilder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public TopUpDTOBuilder topUpMoney(@NotNull @Min(value = 1) @Max(value = 99999) Double topUpMoney) {
            this.topUpMoney = topUpMoney;
            return this;
        }

        public TopUpDTO build() {
            return new TopUpDTO(accountId, topUpMoney);
        }

        public String toString() {
            return "TopUpDTO.TopUpDTOBuilder(accountId=" + this.accountId + ", topUpMoney=" + this.topUpMoney + ")";
        }
    }
}
