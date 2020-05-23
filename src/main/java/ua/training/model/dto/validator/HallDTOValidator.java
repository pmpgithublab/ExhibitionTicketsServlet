package ua.training.model.dto.validator;

import ua.training.model.dto.HallDTO;

import static ua.training.Constants.EMPTY_STRING;

public class HallDTOValidator implements Validator<HallDTO> {
    private static final int HALL_NAME_LENGTH = 200;

    @Override
    public boolean isValid(HallDTO hallDTO) {
        return hallDTO.getName() != null
                && !hallDTO.getName().equals(EMPTY_STRING)
                && hallDTO.getName().length() <= HALL_NAME_LENGTH
                && hallDTO.getNameUK() != null
                && !hallDTO.getNameUK().equals(EMPTY_STRING)
                && hallDTO.getNameUK().length() <= HALL_NAME_LENGTH;
    }
}
