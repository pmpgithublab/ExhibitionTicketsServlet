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

    public static class ExhibitBuilder{
        private final Exhibit exhibit;

        public ExhibitBuilder() {
            this.exhibit = new Exhibit();
        }

        public ExhibitBuilder id(Long id){
            exhibit.setId(id);
            return this;
        }

        public ExhibitBuilder name(String name){
            exhibit.setName(name);
            return this;
        }

        public ExhibitBuilder nameUK(String nameUK){
            exhibit.setNameUK(nameUK);
            return this;
        }

        public ExhibitBuilder startDateTime(LocalDateTime startDateTime){
            exhibit.setStartDateTime(startDateTime);
            return this;
        }

        public ExhibitBuilder endDateTime(LocalDateTime endDateTime){
            exhibit.setEndDateTime(endDateTime);
            return this;
        }

        public ExhibitBuilder maxVisitorsPerDay(int maxVisitorsPerDay){
            exhibit.setMaxVisitorsPerDay(maxVisitorsPerDay);
            return this;
        }

        public ExhibitBuilder ticketCost(long ticketCost){
            exhibit.setTicketCost(ticketCost);
            return this;
        }

        public ExhibitBuilder hallId(Long hallId){
            exhibit.setHallId(hallId);
            return this;
        }

        public Exhibit build(){
            return exhibit;
        }
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
