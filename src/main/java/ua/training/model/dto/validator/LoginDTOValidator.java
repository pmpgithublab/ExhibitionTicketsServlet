package ua.training.model.dto.validator;

import ua.training.util.LocaleUtil;
import ua.training.model.dto.UserDTO;

import static ua.training.Constants.*;

public class LoginDTOValidator implements Validator<UserDTO> {
    @Override
    public boolean isValid(UserDTO userDTO) {
        return userDTO.getEmail() != null
                && userDTO.getEmail().matches(LocaleUtil.getProperty(REGEX_EMAIL_PATTERN))
                && userDTO.getPassword() != null
                && userDTO.getPassword().matches(LocaleUtil.getProperty(REGEX_PASSWORD_PATTERN));
    }
}
