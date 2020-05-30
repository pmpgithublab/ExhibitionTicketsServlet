package ua.training;

public interface Constants {
    String UKRAINIAN_LOCALE_NAME = "uk";
    String ENGLISH_LOCALE_NAME = "en";

    String METHOD_GET = "GET";
    String METHOD_POST = "POST";

    String EMPTY_STRING = "";
    String DOLLAR_SIGN = "$";
    String HRYVNA_SIGN = "₴";
    String SLASH_SYMBOL = "/";
    String REDIRECT_STRING = "redirect:";

    String HALL_NAME = "Hall";
    String EXHIBIT_NAME = "Exhibit";
    String ReportDTOS = "ReportDTOS";

    //    parameter's names
    String PARAM_TICKET_QUANTITY = "quantity";
    String PARAM_PAGE_NUMBER = "page";
    String PARAM_DATE = "date";

    //    session variables
    String USER = "user";
    String USER_NAME = "userName";
    String USER_NAME_UK = "userNameUK";
    String USER_EMAIL = "userEmail";
    String USER_ROLE = "userRole";
    String CURRENCY_SIGN = "currencySign";
    String LANGUAGE = "language";

    //page variables
    String IS_ERROR = "isError";
    String IS_FIELDS_FILLED = "isFieldsFilled";
    String IS_USER_EMAIL_ERROR = "isUserEmailError";
    String NAME_PATTERN_REGEX = "name_pattern_regex";
    String NAME_UK_PATTERN_REGEX = "name_uk_pattern_regex";
    String EMAIL_PATTERN_REGEX = "email_pattern_regex";
    String PASSWORD_PATTERN_REGEX = "password_pattern_regex";
    String HALLS_LIST = "hallsList";
    String EXHIBITS_LIST = "exhibitsList";
    String EXHIBIT_THEMES_LIST = "exhibitThemesList";
    String EXHIBIT_DATES_LIST = "exhibitDatesList";
    String EXHIBIT_SELECTED_THEME = "selectedExhibitTheme";
    String CART = "cart";
    String EXPIRED_TICKETS = "expiredTickets";
    String NOT_ENOUGH_TICKETS = "notEnoughTickets";
    String TOTAL_SUM = "totalSum";
    String TOTAL_QUANTITY = "totalQuantity";

    //    end points
    String WELCOME_PATH = "/";
    String EVERY_PATH = "/*";
    String REGISTRATION_PATH = "/registration";
    String LOGIN_PATH = "/login";
    String LOGOUT_PATH = "/logout";
    String ERROR_PATH = "/error";
    String ADMIN_PATH = "/admin";
    String HALL_EDIT_PATH = "/hall_edit";
    String HALLS_LIST_PATH = "/halls_list";
    String EXHIBIT_EDIT_PATH = "/exhibit_edit";
    String EXHIBITS_LIST_PATH = "/exhibits_list";
    String TRADE_PATH = "/trade";
    String TICKET_ADD_TO_CART_PATH = "/ticket_add";
    String TICKET_DELETE_FROM_CART_PATH = "/ticket_delete";
    String CLEAR_CART_PATH = "/clear_cart";
    String CART_PATH = "/cart";
    String PAYMENT_PATH = "/payment";
    String REPORT_PATH = "/report";


    //    DB, DTO, request, entity field names
    String FIELD_ID = "id";
    String FIELD_NAME = "name";
    String FIELD_NAME_UK = "nameUK";
    String FIELD_DB_ID = "id";
    String FIELD_DB_NAME = "name";
    String FIELD_DB_NAME_EN = "name_en";
    String FIELD_DB_NAME_UK = "name_uk";
    String FIELD_DB_HALL_ID = "hall_id";
    String FIELD_DB_HALL_NAME = "hall_name";
    String FIELD_DB_EXHIBIT_ID = "exhibit_id";
    String FIELD_DB_EXHIBIT_DATE = "exhibit_date";
    String FIELD_DB_START_DATE_TIME = "start_date_time";
    String FIELD_DB_END_DATE_TIME = "end_date_time";
    String FIELD_DB_MAX_VISITORS_PER_DAY = "max_visitors_per_day";
    String FIELD_DB_TICKET_QUANTITY = "tickets_quantity";
    String FIELD_DB_TICKET_COST = "ticket_cost";
    String FIELD_DB_TICKET_SUM = "tickets_sum";
    String FIELD_DB_REMAIN_TICKETS = "remain_tickets";

    //    regex patterns
    String REGEX_NAME_PATTERN = "name.pattern.regexp";
    String REGEX_NAME_UK_PATTERN = "name.uk.pattern.regexp";
    String REGEX_EMAIL_PATTERN = "email.pattern.regexp";
    String REGEX_PASSWORD_PATTERN = "password.pattern.regexp";

    //    logger messages
    String SESSION_ID = ". Session id: ";
    String ROLE = ". Role: ";
    String METHOD = ". Method: ";
    String EMAIL = ". Email: ";
    String TICKET_QUANTITY = ". Ticket quantity: ";
    String EXTERNAL_PAYMENT_SYSTEM_NOT_CONFIRM_TEMPORARY_BLOCK_USER_SUM = "External payment system not confirm temporary block user sum: ";
    String PAYMENT_NOT_ENOUGH_TICKETS = "Payment. Not enough tickets. User id: ";

    //    db messages
    String DB_CONNECTION_CLOSING_ERROR = "DB connection closing error";
}
