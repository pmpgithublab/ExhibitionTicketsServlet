package ua.training.model.entity;

import java.time.LocalDate;

public class Payment {
    private Long id;
    private int ticketQuantity;
    private Long paidSum;
    private LocalDate paidDate;
    private Long userId;

    public Payment() {
    }

    public static class PaymentBuilder {
        private final Payment payment;

        public PaymentBuilder() {
            this.payment = new Payment();
        }

        public Payment.PaymentBuilder id(Long id) {
            payment.setId(id);
            return this;
        }

        public Payment.PaymentBuilder ticketQuantity(int ticketQuantity) {
            payment.setTicketQuantity(ticketQuantity);
            return this;
        }

        public Payment.PaymentBuilder paidSum(Long paidSum) {
            payment.setPaidSum(paidSum);
            return this;
        }

        public Payment.PaymentBuilder paidDate(LocalDate paidDate) {
            payment.setPaidDate(paidDate);
            return this;
        }

        public Payment.PaymentBuilder userId(Long userId) {
            payment.setUserId(userId);
            return this;
        }

        public Payment build() {
            return payment;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public Long getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(Long paidSum) {
        this.paidSum = paidSum;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
