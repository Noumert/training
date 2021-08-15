package project.model;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import project.controller.GlobalConstants;

import java.util.Locale;

@Component
public class MoneyParser {
    public long getMoneyValue(double money) {
        long moneyValue;
        Locale locale = LocaleContextHolder.getLocale();
        if (GlobalConstants.UA_LOCALE.equals(locale)) {
            moneyValue = (long) (money * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT);
        } else {
            moneyValue = (long) (money * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT * GlobalConstants.DOLLAR_COEFFICIENT);
        }
        return moneyValue;
    }
}
