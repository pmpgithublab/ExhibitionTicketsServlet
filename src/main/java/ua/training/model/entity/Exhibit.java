package ua.training.model.entity;

import ua.training.model.dto.ExhibitDTO;

import java.time.LocalDateTime;

public class Exhibit {
    private Long id;
    private String name;
    private String nameUK;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int maxVisitorsPerDay;
    private long ticketCost;
    private Long hallId;

    public Exhibit(ExhibitDTO exhibitDTO) {
        if (exhibitDTO.getId() != null) {
            this.id = exhibitDTO.getId();
        }
        this.id = exhibitDTO.getId();
        this.name = exhibitDTO.getName();
        this.nameUK = exhibitDTO.getNameUK();
        this.startDateTime = exhibitDTO.getStartDateTime();
        this.endDateTime = exhibitDTO.getEndDateTime();
        this.maxVisitorsPerDay = exhibitDTO.getMaxVisitorsPerDay();
        this.ticketCost = exhibitDTO.getTicketCost();
        this.hallId = exhibitDTO.getHallId();
    }

    public Exhibit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUK() {
        return nameUK;
    }

    public void setNameUK(String nameUK) {
        this.nameUK = nameUK;
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

    public int getMaxVisitorsPerDay() {
        return maxVisitorsPerDay;
    }

    public void setMaxVisitorsPerDay(int maxVisitorsPerDay) {
        this.maxVisitorsPerDay = maxVisitorsPerDay;
    }

    public long getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(long ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }
}
