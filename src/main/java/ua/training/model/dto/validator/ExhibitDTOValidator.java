package ua.training.model.dto.validator;

import ua.training.model.dto.ExhibitDTO;

import static ua.training.Constants.*;

public class ExhibitDTOValidator implements Validator<ExhibitDTO> {
    private static final int EXHIBIT_NAME_LENGTH = 200;


    @Override
    public boolean isValid(ExhibitDTO exhibitDTO) {
        return exhibitDTO.getName() != null
                && !exhibitDTO.getName().equals(EMPTY_STRING)
                && exhibitDTO.getName().length() <= EXHIBIT_NAME_LENGTH
                && exhibitDTO.getNameUK() != null
                && !exhibitDTO.getNameUK().equals(EMPTY_STRING)
                && exhibitDTO.getNameUK().length() <= EXHIBIT_NAME_LENGTH
                && exhibitDTO.getStartDateTime() != null
                && exhibitDTO.getEndDateTime() != null
                && exhibitDTO.getMaxVisitorsPerDay() > 0
                && exhibitDTO.getTicketCost() > 0L
                && exhibitDTO.getHallId() != null;
    }
}
