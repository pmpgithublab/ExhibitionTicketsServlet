package ua.training.model.dto;

public class AdminStatisticDTO {
    private String userName;
    private int ticketQuantity;
    private long paidSum;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public long getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(long paidSum) {
        this.paidSum = paidSum;
    }
}
