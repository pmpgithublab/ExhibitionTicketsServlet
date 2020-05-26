package ua.training.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserCartDTO {
    private Long ticketId;
    private LocalDate exhibitDate;
    private Long exhibitId;
    private String exhibitName;
    private String hallName;
    private int ticketQuantity;
    private long ticketSum;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int unsoldTicketsQuantity;


    public static class UserCartDTOBuilder {
        private final UserCartDTO userCartDTO;

        public UserCartDTOBuilder() {
            this.userCartDTO = new UserCartDTO();
        }

        public UserCartDTOBuilder ticketId(Long ticketId) {
            userCartDTO.setTicketId(ticketId);
            return this;
        }

        public UserCartDTOBuilder exhibitDate(LocalDate exhibitDate) {
            userCartDTO.setExhibitDate(exhibitDate);
            return this;
        }

        public UserCartDTOBuilder exhibitId(Long exhibitId) {
            userCartDTO.setExhibitId(exhibitId);
            return this;
        }

        public UserCartDTOBuilder exhibitName(String exhibitName) {
            userCartDTO.setExhibitName(exhibitName);
            return this;
        }

        public UserCartDTOBuilder hallName(String hallName) {
            userCartDTO.setHallName(hallName);
            return this;
        }

        public UserCartDTOBuilder ticketQuantity(int ticketQuantity) {
            userCartDTO.setTicketQuantity(ticketQuantity);
            return this;
        }

        public UserCartDTOBuilder ticketSum(long ticketSum) {
            userCartDTO.setTicketSum(ticketSum);
            return this;
        }

        public UserCartDTOBuilder startDateTime(LocalDateTime startDateTime) {
            userCartDTO.setStartDateTime(startDateTime);
            return this;
        }

        public UserCartDTOBuilder endDateTime(LocalDateTime endDateTime) {
            userCartDTO.setEndDateTime(endDateTime);
            return this;
        }

        public UserCartDTOBuilder unsoldTicketsQuantity(int unsoldTicketsQuantity) {
            userCartDTO.setUnsoldTicketsQuantity(unsoldTicketsQuantity);
            return this;
        }

        public UserCartDTO build() {
            return userCartDTO;
        }
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public void setExhibitId(Long exhibitIId) {
        this.exhibitId = exhibitIId;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getUnsoldTicketsQuantity() {
        return unsoldTicketsQuantity;
    }

    public void setUnsoldTicketsQuantity(int unsoldTicketsQuantity) {
        this.unsoldTicketsQuantity = unsoldTicketsQuantity;
    }
}
