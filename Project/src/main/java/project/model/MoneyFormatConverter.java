package project.model;

import project.controller.GlobalConstants;

public interface MoneyFormatConverter {
    long getMoneyValue(String money);

    String getStringMoneyFromMoneyValue(Long moneyValue);

    long getMoneyValue(double money);
}
