package ua.training.model.dto;

import ua.training.model.entity.Ticket;

import java.time.LocalDate;

public class TicketDTO {
    private Long id;
    private LocalDate exhibitDate;
    private Long exhibitId;
    private String exhibitName;
    private Long hallId;
    private String hallName;
    private int ticketQuantity;
    private long ticketSum;
    private Long paymentId;
    private Long userId;


    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.exhibitDate = ticket.getExhibitDate();
        this.ticketQuantity = ticket.getTicketQuantity();
        this.ticketSum = ticket.getTicketSum();
        this.paymentId = ticket.getPaymentId();
        this.userId = ticket.getUserId();
    }

    public TicketDTO() {
    }

    public static class TicketDTOBuilder {
        private final TicketDTO ticketDTO;

        public TicketDTOBuilder() {
            this.ticketDTO = new TicketDTO();
        }

        public TicketDTO.TicketDTOBuilder id(Long id) {
            ticketDTO.setId(id);
            return this;
        }

        public TicketDTO.TicketDTOBuilder exhibitDate(LocalDate exhibitDate) {
            ticketDTO.setExhibitDate(exhibitDate);
            return this;
        }

        public TicketDTO.TicketDTOBuilder exhibitId(Long exhibitId) {
            ticketDTO.setExhibitId(exhibitId);
            return this;
        }

        public TicketDTO.TicketDTOBuilder exhibitName(String exhibitName) {
            ticketDTO.setExhibitName(exhibitName);
            return this;
        }

        public TicketDTO.TicketDTOBuilder hallId(Long hallId) {
            ticketDTO.setHallId(hallId);
            return this;
        }

        public TicketDTO.TicketDTOBuilder hallName(String hallName) {
            ticketDTO.setHallName(hallName);
            return this;
        }

        public TicketDTO.TicketDTOBuilder ticketQuantity(int ticketQuantity) {
            ticketDTO.setTicketQuantity(ticketQuantity);
            return this;
        }

        public TicketDTO.TicketDTOBuilder ticketSum(long ticketSum) {
            ticketDTO.setTicketSum(ticketSum);
            return this;
        }

        public TicketDTO.TicketDTOBuilder paymentId(Long paymentId) {
            ticketDTO.setPaymentId(paymentId);
            return this;
        }

        public TicketDTO.TicketDTOBuilder userId(Long userId) {
            ticketDTO.setUserId(userId);
            return this;
        }

        public TicketDTO build() {
            return ticketDTO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Long getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(Long exhibitId) {
        this.exhibitId = exhibitId;
    }

    public String getExhibitName() {
        return exhibitName;
    }

    public void setExhibitName(String exhibitName) {
        this.exhibitName = exhibitName;
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
