package projectServlet.model.entity;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Noumert on 12.08.2021.
 */
public class Payment {
    private Long payment_id;
    private String paymentNumber;
    private Long payment_money;
    private LocalDateTime dateTime;
    private String recipient;
    private StatusType status;
    private Account account;

    public Payment(Long id, String paymentNumber, Long money, LocalDateTime dateTime, String recipient, StatusType status, Account account) {
        this.payment_id = id;
        this.paymentNumber = paymentNumber;
        this.payment_money = money;
        this.dateTime = dateTime;
        this.recipient = recipient;
        this.status = status;
        this.account = account;
    }

    public Payment() {
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;

        return Objects.equals(payment_id, payment.payment_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment_id);
    }

    public Long getId() {
        return this.payment_id;
    }

    public String getPaymentNumber() {
        return this.paymentNumber;
    }

    public Long getMoney() {
        return this.payment_money;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setId(Long id) {
        this.payment_id = id;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setMoney(Long payment_money) {
        this.payment_money = payment_money;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        return "Payment(id=" + this.getId() + ", paymentNumber=" + this.getPaymentNumber() + ", money=" + this.getMoney() + ", dateTime=" + this.getDateTime() + ", recipient=" + this.getRecipient() + ", status=" + this.getStatus() + ", account=" + this.getAccount() + ")";
    }

    public static class PaymentBuilder {
        private Long payment_id;
        private String paymentNumber;
        private Long payment_money;
        private LocalDateTime dateTime;
        private String recipient;
        private StatusType status;
        private Account account;

        PaymentBuilder() {
        }

        public PaymentBuilder id(Long id) {
            this.payment_id = id;
            return this;
        }

        public PaymentBuilder paymentNumber(String paymentNumber) {
            this.paymentNumber = paymentNumber;
            return this;
        }

        public PaymentBuilder money(Long money) {
            this.payment_money = money;
            return this;
        }

        public PaymentBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public PaymentBuilder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public PaymentBuilder status(StatusType status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public Payment build() {
            return new Payment(payment_id, paymentNumber, payment_money, dateTime, recipient, status, account);
        }

        public String toString() {
            return "Payment.PaymentBuilder(id=" + this.payment_id + ", paymentNumber=" + this.paymentNumber + ", money=" + this.payment_money + ", dateTime=" + this.dateTime + ", recipient=" + this.recipient + ", status=" + this.status + ", account=" + this.account + ")";
        }
    }
}
