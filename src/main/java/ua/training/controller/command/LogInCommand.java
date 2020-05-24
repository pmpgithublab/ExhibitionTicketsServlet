package ua.training.controller.command;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.util.SecurityUtil;
import ua.training.model.dto.UserDTO;
import ua.training.model.dto.validator.LoginDTOValidator;
import ua.training.model.entity.UserRole;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ua.training.Constants.*;

public class LogInCommand implements Command {
    private static final Logger log = Logger.getLogger(LogInCommand.class);
    private static final String LOGIN_PAGE = "/WEB-INF/login.jsp";
    private static final String USER_LOGGED_IN = "User is logged in. Email: ";
    private static final String USER_REDIRECT_BY_ROLE_ERROR = "User redirect by role error. Unknown role: ";

    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (request.getMethod().equals(METHOD_GET)) {
            return LOGIN_PAGE;
        }

        if (request.getMethod().equals(METHOD_POST)) {
            return processLogin(request);
        }

        log.warn(MessageUtil.getUnacceptedMethodMessage(request));
        return REDIRECT_STRING + ERROR_PATH;
    }

    private String processLogin(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO(request);

        if (isUserDTOValid(userDTO)) {

            return checkInDB(request, userDTO);
        }

        request.setAttribute(IS_ERROR, true);

        return LOGIN_PAGE;
    }

    private boolean isUserDTOValid(UserDTO userDTO) {
        return new LoginDTOValidator().isValid(userDTO);
    }

    private String checkInDB(HttpServletRequest request, UserDTO userDTO) {
        userDTO.setPassword(SecurityUtil.encryptString(userDTO.getPassword()));
        Optional<UserDTO> userFromDB = userService.findUserByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());

        if (userFromDB.isPresent()) {
            setSessionVariables(request, userFromDB.get());
            log.info(USER_LOGGED_IN + userFromDB.get().getEmail() + SESSION_ID + request.getSession().getId());

            return redirectToUserPage(request, userFromDB.get());
        } else {
            request.setAttribute(IS_ERROR, true);
            return LOGIN_PAGE;
        }
    }

    private void setSessionVariables(HttpServletRequest request, UserDTO userDTO) {
        HttpSession session = request.getSession();
        session.setAttribute(USER, userDTO);
        session.setAttribute(USER_NAME, userDTO.getName());
        session.setAttribute(USER_NAME_UK, userDTO.getNameUK());
        session.setAttribute(USER_EMAIL, userDTO.getEmail());
        session.setAttribute(USER_ROLE, userDTO.getRole());
    }

    private String redirectToUserPage(HttpServletRequest request, UserDTO userFromDB) {
        if (userFromDB.getRole().equals(UserRole.ROLE_ADMIN)) {
            return REDIRECT_STRING + ADMIN_PATH + EXHIBITS_LIST_PATH;
        }
        if (userFromDB.getRole().equals(UserRole.ROLE_USER)) {
            return REDIRECT_STRING + TRADE_PATH + EXHIBITS_LIST_PATH;
        }

        log.error(USER_REDIRECT_BY_ROLE_ERROR + ROLE + userFromDB.getRole() + EMAIL + userFromDB.getEmail());
        new LogOutCommand().execute(request);
        return REDIRECT_STRING + ERROR_PATH;
    }
}
