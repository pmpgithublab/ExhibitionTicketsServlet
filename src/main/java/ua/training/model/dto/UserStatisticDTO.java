package ua.training.model.dto;

import java.time.LocalDate;

public class UserStatisticDTO {
    private LocalDate exhibitDate;
    private String exhibitName;
    private int ticketQuantity;
    private long paidSum;


    public LocalDate getExhibitDate() {
        return exhibitDate;
    }

    public void setExhibitDate(LocalDate exhibitDate) {
        this.exhibitDate = exhibitDate;
    }

    public String getExhibitName() {
        return exhibitName;
    }

    public void setExhibitName(String exhibitName) {
        this.exhibitName = exhibitName;
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
