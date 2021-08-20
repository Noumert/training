package project.model;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import project.controller.GlobalConstants;

import java.util.Locale;

@Component
public class MoneyFormatConverter {
    public long getMoneyValue(String money) {
        try {
            return (long) (Double.parseDouble(money) * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public String getStringMoneyFromMoneyValue(Long moneyValue) {
        return String.format("%.2f",(double)moneyValue/100);
    }

    public long getMoneyValue(double money) {
        return (long) (money * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT);
    }
}
