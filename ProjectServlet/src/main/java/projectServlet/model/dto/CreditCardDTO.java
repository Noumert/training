package projectServlet.model.dto;

/**
 * Created by Noumert on 13.08.2021.
 */
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String expirationDate;

    public CreditCardDTO(Long id, String cardNumber, String expirationDate) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public CreditCardDTO() {
    }

    public static CreditCardDTOBuilder builder() {
        return new CreditCardDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreditCardDTO)) return false;
        final CreditCardDTO other = (CreditCardDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$cardNumber = this.getCardNumber();
        final Object other$cardNumber = other.getCardNumber();
        if (this$cardNumber == null ? other$cardNumber != null : !this$cardNumber.equals(other$cardNumber))
            return false;
        final Object this$expirationDate = this.getExpirationDate();
        final Object other$expirationDate = other.getExpirationDate();
        if (this$expirationDate == null ? other$expirationDate != null : !this$expirationDate.equals(other$expirationDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreditCardDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $cardNumber = this.getCardNumber();
        result = result * PRIME + ($cardNumber == null ? 43 : $cardNumber.hashCode());
        final Object $expirationDate = this.getExpirationDate();
        result = result * PRIME + ($expirationDate == null ? 43 : $expirationDate.hashCode());
        return result;
    }

    public String toString() {
        return "CreditCardDTO(id=" + this.getId() + ", cardNumber=" + this.getCardNumber() + ", expirationDate=" + this.getExpirationDate() + ")";
    }

    public static class CreditCardDTOBuilder {
        private Long id;
        private String cardNumber;
        private String expirationDate;

        CreditCardDTOBuilder() {
        }

        public CreditCardDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CreditCardDTOBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CreditCardDTOBuilder expirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public CreditCardDTO build() {
            return new CreditCardDTO(id, cardNumber, expirationDate);
        }

        public String toString() {
            return "CreditCardDTO.CreditCardDTOBuilder(id=" + this.id + ", cardNumber=" + this.cardNumber + ", expirationDate=" + this.expirationDate + ")";
        }
    }
}
