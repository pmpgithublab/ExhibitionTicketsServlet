package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.LocaleUtil;
import ua.training.util.MessageUtil;
import ua.training.util.SecurityUtil;
import ua.training.model.dto.UserDTO;
import ua.training.model.dto.validator.RegistrationDTOValidator;
import ua.training.model.exception.SuchEmailExistsException;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private static final String REGISTRATION_PAGE = "/WEB-INF/registration.jsp";

    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals(METHOD_GET)) {
            setupNewRegistrationPage(request, true, false);

            return REGISTRATION_PAGE;
        }

        if (request.getMethod().equals(METHOD_POST)) {
            return processRegistration(request);
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String processRegistration(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO(request);

        if (!isUserDTOValid(userDTO)) {
            setupNewRegistrationPage(request, false, false);
            setupRepeatRegistrationPage(request, userDTO);

            return REGISTRATION_PAGE;
        }

        try {
            userDTO.setPassword(SecurityUtil.encryptString(userDTO.getPassword()));
            userService.saveUser(userDTO);
        } catch (SuchEmailExistsException e) {
            log.info(EMAIL_EXIST + e.getLogin() + SESSION_ID + request.getSession().getId());
            setupNewRegistrationPage(request, true, true);
            setupRepeatRegistrationPage(request, userDTO);

            return REGISTRATION_PAGE;
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            return REDIRECT_STRING + ERROR_PATH;
        }

        return REDIRECT_STRING + WELCOME_PATH;
    }

    private boolean isUserDTOValid(UserDTO userDTO) {
        return new RegistrationDTOValidator().isValid(userDTO);
    }

    private void setupNewRegistrationPage(HttpServletRequest request, boolean isFieldsCorrect, boolean isEmailRegistered) {
        request.setAttribute(IS_FIELDS_FILLED, isFieldsCorrect);
        request.setAttribute(IS_USER_EMAIL_ERROR, isEmailRegistered);
        request.setAttribute(NAME_PATTERN_REGEX, LocaleUtil.getProperty(REGEX_NAME_PATTERN));
        request.setAttribute(NAME_UK_PATTERN_REGEX, LocaleUtil.getLocalNameRegexName());
        request.setAttribute(EMAIL_PATTERN_REGEX, LocaleUtil.getProperty(REGEX_EMAIL_PATTERN));
        request.setAttribute(PASSWORD_PATTERN_REGEX, LocaleUtil.getProperty(REGEX_PASSWORD_PATTERN));
    }

    private void setupRepeatRegistrationPage(HttpServletRequest request, UserDTO userDTO) {
        request.setAttribute(USER_NAME, userDTO.getName());
        request.setAttribute(USER_NAME_UK, userDTO.getNameUK());
        request.setAttribute(USER_EMAIL, userDTO.getEmail());
    }
}
