package ua.training.model.dto;

import ua.training.model.entity.Exhibit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static ua.training.Constants.*;

public class ExhibitDTO implements Cloneable {
    private static final String TICKET_COST_REGEX = "^\\d{1,7}\\.\\d{2}$";
    private static final String POINT_SIGN_REGEX = "\\.";

    private Long id;
    private String name;
    private String nameUK;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int maxVisitorsPerDay;
    private long ticketCost;
    private Long hallId;
    private String hallName;
    private LocalDate exhibitDate;

    public ExhibitDTO(Exhibit exhibit) {
        this.id = exhibit.getId();
        this.name = exhibit.getName();
        this.nameUK = exhibit.getNameUK();
        this.startDateTime = exhibit.getStartDateTime();
        this.endDateTime = exhibit.getEndDateTime();
        this.maxVisitorsPerDay = exhibit.getMaxVisitorsPerDay();
        this.ticketCost = exhibit.getTicketCost();
        this.hallId = exhibit.getHallId();
    }

    public ExhibitDTO(HttpServletRequest request) {
        this.id = parseLong(request.getParameter(FIELD_ID));
        this.name = request.getParameter(FIELD_NAME);
        this.nameUK = request.getParameter(FIELD_NAME_UK);
        this.startDateTime = parseDateTime(request.getParameter(FIELD_START_DATE_TIME));
        this.endDateTime = parseDateTime(request.getParameter(FIELD_END_DATE_TIME));
        this.maxVisitorsPerDay = parseInteger(request.getParameter(FIELD_MAX_VISITORS_PER_DAY));
        this.ticketCost = parseTicketCost(request);
        this.hallId = parseLong(request.getParameter(FIELD_HALL_ID));
    }

    public ExhibitDTO() {
    }

    private LocalDateTime parseDateTime(String str) {
        try {
            return LocalDateTime.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return null;
        }
    }

    private long parseTicketCost(HttpServletRequest request) {
        String stringTicketCost = request.getParameter(FIELD_TICKET_COST);
        if (stringTicketCost.matches(TICKET_COST_REGEX)) {
            Long tc = parseLong(stringTicketCost.replaceAll(POINT_SIGN_REGEX, EMPTY_STRING));
            return tc == null ? 0 : tc;
        }

        return 0;
    }

    @Override
    public ExhibitDTO clone() throws CloneNotSupportedException {
        return (ExhibitDTO) super.clone();
    }

    public static class ExhibitDTOBuilder{
        private final ExhibitDTO exhibitDTO;

        public ExhibitDTOBuilder() {
            this.exhibitDTO = new ExhibitDTO();
        }

        public ExhibitDTOBuilder id(Long id){
            exhibitDTO.setId(id);
            return this;
        }

        public ExhibitDTOBuilder name(String name){
            exhibitDTO.setName(name);
            return this;
        }

        public ExhibitDTOBuilder nameUK(String nameUK){
            exhibitDTO.setNameUK(nameUK);
            return this;
        }

        public ExhibitDTOBuilder startDateTime(LocalDateTime startDateTime){
            exhibitDTO.setStartDateTime(startDateTime);
            return this;
        }

        public ExhibitDTOBuilder endDateTime(LocalDateTime endDateTime){
            exhibitDTO.setEndDateTime(endDateTime);
            return this;
        }

        public ExhibitDTOBuilder maxVisitorsPerDay(int maxVisitorsPerDay){
            exhibitDTO.setMaxVisitorsPerDay(maxVisitorsPerDay);
            return this;
        }

        public ExhibitDTOBuilder ticketCost(long ticketCost){
            exhibitDTO.setTicketCost(ticketCost);
            return this;
        }

        public ExhibitDTOBuilder hallId(Long hallId){
            exhibitDTO.setHallId(hallId);
            return this;
        }

        public ExhibitDTOBuilder hallName(String hallName){
            exhibitDTO.setHallName(hallName);
            return this;
        }

        public ExhibitDTOBuilder exhibitDate(LocalDate exhibitDate){
            exhibitDTO.setExhibitDate(exhibitDate);
            return this;
        }

        public ExhibitDTO build(){
            return exhibitDTO;
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

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public LocalDate getExhibitDate() {
        return exhibitDate;
    }

    public void setExhibitDate(LocalDate exhibitDate) {
        this.exhibitDate = exhibitDate;
    }
}
