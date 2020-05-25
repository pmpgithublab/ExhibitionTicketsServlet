package ua.training.model.dto.validator;

import ua.training.model.dto.ExhibitDTO;
import ua.training.util.CheckUtils;

import java.time.LocalDateTime;

public class ExhibitDTOValidator implements Validator<ExhibitDTO> {
    private static final int EXHIBIT_NAME_LENGTH = 200;


    @Override
    public boolean isValid(ExhibitDTO exhibitDTO) {
        return !CheckUtils.isNullOrEmptyString(exhibitDTO.getName())
                && exhibitDTO.getName().length() <= EXHIBIT_NAME_LENGTH
                && !CheckUtils.isNullOrEmptyString(exhibitDTO.getNameUK())
                && exhibitDTO.getNameUK().length() <= EXHIBIT_NAME_LENGTH
                && exhibitDTO.getStartDateTime() != null
                && exhibitDTO.getStartDateTime().isAfter(LocalDateTime.now())
                && exhibitDTO.getEndDateTime() != null
                && exhibitDTO.getEndDateTime().isAfter(LocalDateTime.now())
                && exhibitDTO.getMaxVisitorsPerDay() > 0
                && exhibitDTO.getTicketCost() > 0L
                && exhibitDTO.getHallId() != null;
    }
}
