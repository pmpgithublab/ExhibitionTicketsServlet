package ua.training.util;

import ua.training.model.dto.ExhibitDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import static ua.training.Constants.*;

public class CheckUtils {
    private static final String IS_NUMBER_REGEX = "\\d+";


    private static boolean isPositiveNumber(String positiveNumber) {
        return positiveNumber != null && !positiveNumber.equals(EMPTY_STRING) && positiveNumber.matches(IS_NUMBER_REGEX);
    }

    public static boolean isPositiveInteger(String positiveNumber) {
        if (isPositiveNumber(positiveNumber)){
            try {
                Integer.parseInt(positiveNumber);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public static boolean isPositiveLong(String positiveNumber) {
        if (isPositiveNumber(positiveNumber)){
            try {
                Long.parseLong(positiveNumber);

                return true;
            } catch (Exception e) {

                return false;
            }
        }

        return false;
    }

    public static boolean isTicketQuantityValid(String id) {
        return isPositiveInteger(id) && Integer.parseInt(id) < MAX_TICKET_QUANTITY;
    }

    public static boolean isDate(String localDate) {
        try {
            LocalDate.parse(localDate);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static LocalDate getMaxDate(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2) ? date1 : date2;
    }

    public static boolean isExhibitDateTimeActual(LocalDate ticketExhibitDate, ExhibitDTO exhibitDTO) {
        LocalDate startDate = exhibitDTO.getStartDateTime().toLocalDate();
        LocalDate endDate = exhibitDTO.getEndDateTime().toLocalDate();
        LocalTime timeEndExhibit = exhibitDTO.getEndDateTime().toLocalTime();

        return isExhibitDateTimeActual(ticketExhibitDate, startDate, endDate, timeEndExhibit);
    }

    public static boolean isExhibitDateTimeActual(LocalDate ticketExhibitDate, LocalDate startDate,
                                                            LocalDate endDate, LocalTime timeExhibitEnd) {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        return !ticketExhibitDate.isBefore(dateNow)
                && !ticketExhibitDate.isBefore(startDate) && !ticketExhibitDate.isAfter(endDate)
                && (!ticketExhibitDate.isEqual(dateNow) || !timeNow.isAfter(timeExhibitEnd));
    }
}