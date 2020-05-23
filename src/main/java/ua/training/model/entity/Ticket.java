package ua.training.model.entity;

import ua.training.model.dto.TicketDTO;

import java.time.LocalDate;

public class Ticket {
    private Long id;
    private LocalDate exhibitDate;
    private int ticketQuantity;
    private long ticketSum;
    private Long hallId;
    private Long exhibitId;
    private Long paymentId;
    private Long userId;

    public Ticket(TicketDTO ticketDTO) {
        this.id = ticketDTO.getId();
        this.exhibitDate = ticketDTO.getExhibitDate();
        this.ticketQuantity = ticketDTO.getTicketQuantity();
        this.ticketSum = ticketDTO.getTicketSum();
        this.hallId = ticketDTO.getHallId();
        this.exhibitId = ticketDTO.getExhibitId();
        this.paymentId = ticketDTO.getPaymentId();
        this.userId = ticketDTO.getUserId();
    }

    public Ticket() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExhibitDate() {
        return exhibitDate;
    }

    public void setExhibitDate(LocalDate exhibitDate) {
        this.exhibitDate = exhibitDate;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public long getTicketSum() {
        return ticketSum;
    }

    public void setTicketSum(long ticketSum) {
        this.ticketSum = ticketSum;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(Long exhibitId) {
        this.exhibitId = exhibitId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
