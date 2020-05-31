package ua.training.model.entity;

import ua.training.model.dto.HallDTO;

public class Hall {
    private Long id;
    private String name;
    private String nameUK;

    public Hall(HallDTO hallDTO) {
        this.id = hallDTO.getId();
        this.name = hallDTO.getName();
        this.nameUK = hallDTO.getNameUK();
    }

    public Hall() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id.equals(hall.id) &&
                name.equals(hall.name) &&
                nameUK.equals(hall.nameUK);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + nameUK.hashCode();
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
