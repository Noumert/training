package project.model;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import project.controller.GlobalConstants;

import java.util.Locale;

/**
 * Created by Noumert on 21.08.2021.
 */
@Component
public class MoneyFormatConverterImpl implements MoneyFormatConverter {
    /**
     * Convert String money in double format into long moneyValue
     * @param money
     * @return moneyValue
     */
    public long getMoneyValue(String money) {
        try {
            return (long) (Double.parseDouble(money) * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    /**
     * Convert Long moneyValue into formatted String money
     * @param moneyValue
     * @return money
     */
    public String getStringMoneyFromMoneyValue(Long moneyValue) {
        return String.format("%.2f",(double)moneyValue/100);
    }

    /**
     * Convert double money into long moneyValue
     * @param money
     * @return moneyValue
     */
    public long getMoneyValue(double money) {
        return (long) (money * GlobalConstants.MONEY_TO_VALUE_COEFFICIENT);
    }
}
