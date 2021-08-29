package projectServlet.model.converters;


import projectServlet.model.GlobalConstants;

/**
 * Created by Noumert on 21.08.2021.
 */
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
        return String.format("%.2f",(double)moneyValue/100).replace(',','.');
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
