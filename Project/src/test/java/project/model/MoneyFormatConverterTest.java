package project.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MoneyFormatConverterTest {
    private MoneyFormatConverter moneyFormatConverter;

    @BeforeAll
    public void init() {
        moneyFormatConverter = new MoneyFormatConverterImpl();
    }

    @Test
    void getMoneyValueCorrectString() {
        assertThat(moneyFormatConverter.getMoneyValue("31.22")).isEqualTo(3122L);
    }

    @Test
    void getMoneyValueIncorrectString() {
        assertThat(moneyFormatConverter.getMoneyValue("Hello")).isEqualTo(0L);
    }

    @Test
    void getMoneyValueDouble() {
        assertThat(moneyFormatConverter.getMoneyValue(31.222222)).isEqualTo(3122L);
    }

    @Test
    void getStringMoneyFromMoneyValue() {
        assertThat(moneyFormatConverter.getStringMoneyFromMoneyValue(3122L)).isEqualTo("31.22");
    }
}