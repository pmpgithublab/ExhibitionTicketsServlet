package ua.training.controller.util;

import ua.training.model.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.USER;

public class Util {
    public static Long getUserId(HttpServletRequest request) {
        return ((UserDTO) request.getSession().getAttribute(USER)).getId();
    }
}
