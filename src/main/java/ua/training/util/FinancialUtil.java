package ua.training.util;

import java.util.Random;

import static ua.training.Constants.DOLLAR_SIGN;
import static ua.training.Constants.EXCHANGE_RATE;

public class FinancialUtil {
    public static long calcCost(long sum) {
        if (LocaleUtil.isEnglish()) {
            return sum;
        }
        return sum * EXCHANGE_RATE;
    }

    public static long getAccountingSum(long sum, String currency) {
        if (currency.equals(DOLLAR_SIGN)) {
            return sum;
        }
        return sum / EXCHANGE_RATE;
    }

    public static boolean askExternalPaymentSystemTemporaryBlockUserSum(Long userId, Long lockSum, int lockTime) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(5) <= 3;
    }

    public static void confirmPayment(Long userId, Long totalSum) {

    }
}
