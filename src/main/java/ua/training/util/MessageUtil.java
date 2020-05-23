package ua.training.util;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class MessageUtil {
    private static final String MESSAGE = " Message: ";
    private static final String REQUESTED_UNACCEPTED_METHOD = "Requested unaccepted method by path: ";
    private static final String RECEIVED_INVALID_PARAMETER = "Received invalid parameter(s) by path: ";
    private static final String OBJECT_BY_ID_NOT_FOUND = " by id not found: ";
    private static final String OBJECT_SAVING_ERROR = " saving error. Path: ";
    private static final String OBJECT_SAVED = " saved. Path: ";
    private static final String OBJECT_NOT_VALID = " not valid. Path: ";
    private static final String RUNTIME_EXCEPTION = "Runtime exception: ";


    public static String getUnacceptedMethodMessage(HttpServletRequest request) {
        return REQUESTED_UNACCEPTED_METHOD + request.getServletPath()
                + METHOD + request.getMethod()
                + SESSION_ID + request.getSession().getId();
    }

    public static String getInvalidParameterMessage(HttpServletRequest request) {
        return RECEIVED_INVALID_PARAMETER + request.getServletPath()
                + PARAMETERS + request.getQueryString()
                + SESSION_ID + request.getSession().getId();
    }

    public static String getObjectByIdNotFoundMessage(HttpServletRequest request, String objectName) {
        return objectName + OBJECT_BY_ID_NOT_FOUND + request.getServletPath()
                + PARAMETERS + request.getQueryString()
                + SESSION_ID + request.getSession().getId();
    }

    public static String getObjectByIdNotFoundMessage(Long objectId, String objectName) {
        return objectName + OBJECT_BY_ID_NOT_FOUND + objectId;
    }

    public static String getObjectSaveErrorMessage(HttpServletRequest request, String objectName, Exception exception) {
        return objectName + OBJECT_SAVING_ERROR + request.getServletPath() + MESSAGE + exception.getMessage()
                + SESSION_ID + request.getSession().getId();
    }

    public static String getObjectSaveMessage(HttpServletRequest request,
                                              String objectName, String additionalInformation) {

        return objectName + OBJECT_SAVED + request.getServletPath()
                + SESSION_ID + request.getSession().getId() + additionalInformation;
    }

    public static String getObjectNotValid(HttpServletRequest request, String objectName) {
        return objectName + OBJECT_NOT_VALID + request.getServletPath() + request.getSession().getId();
    }

    public static String getRuntimeExceptionMessage(Exception exception) {
        return RUNTIME_EXCEPTION + exception.getMessage();
    }

    public static String getRuntimeExceptionMessage(String message) {
        return RUNTIME_EXCEPTION + message;
    }
}
