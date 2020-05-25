package ua.training.model.dto.validator;

import ua.training.model.dto.HallDTO;
import ua.training.util.CheckUtils;

public class HallDTOValidator implements Validator<HallDTO> {
    private static final int HALL_NAME_LENGTH = 200;

    @Override
    public boolean isValid(HallDTO hallDTO) {
        return !CheckUtils.isNullOrEmptyString(hallDTO.getName())
                && hallDTO.getName().length() <= HALL_NAME_LENGTH
                && !CheckUtils.isNullOrEmptyString(hallDTO.getNameUK())
                && hallDTO.getNameUK().length() <= HALL_NAME_LENGTH;
    }
}
