package ua.training.model.dto;

import ua.training.model.entity.Hall;
import ua.training.util.CheckUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static ua.training.Constants.*;

public class HallDTO {
    private Long id;
    private String name;
    private String nameUK;

    public HallDTO(HttpServletRequest request) {
        String s = request.getParameter(FIELD_ID);
        if (CheckUtils.isPositiveLong(s)) {
            this.id = Long.parseLong(s);
        }
        this.name = request.getParameter(FIELD_NAME);
        this.nameUK = request.getParameter(FIELD_NAME_UK);
    }

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.nameUK = hall.getNameUK();
    }

    public HallDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallDTO hallDTO = (HallDTO) o;
        return id.equals(hallDTO.id) &&
                name.equals(hallDTO.name) &&
                nameUK.equals(hallDTO.nameUK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameUK);
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
}
