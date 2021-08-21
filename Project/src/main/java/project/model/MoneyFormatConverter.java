package project.model;

import project.controller.GlobalConstants;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface MoneyFormatConverter {
    long getMoneyValue(String money);

    String getStringMoneyFromMoneyValue(Long moneyValue);

    long getMoneyValue(double money);
}
