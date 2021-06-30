import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {

    //Passed
    @Ignore
    @Test
    public void setPrimaryBarrierIsBarrierValuesPossibleTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 10);
        boolean isMin = false;
        boolean isMax = false;
        int secretNumber;
        for (int i = 0; i < 1000; i++) {
            secretNumber = model.setSecretValue();
            if (secretNumber == 1) {
                isMin = true;
            }
            if (secretNumber == 9) {
                isMax = true;
            }
        }
        Assert.assertTrue((isMin && isMax));
    }

    //Passed
    @Ignore
    @Test
    public void setPrimaryBarrierIsOutOfBarrierValuesImpossibleTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 10);
        boolean isLessThanMin = false;
        boolean isMoreThanMax = false;
        int secretNumber;
        for (int i = 0; i < 1000; i++) {
            secretNumber = model.setSecretValue();
            if (secretNumber <= 0) {
                isLessThanMin = true;
            }
            if (secretNumber >= 10) {
                isMoreThanMax = true;
            }
        }
        Assert.assertFalse((isLessThanMin || isMoreThanMax));
    }

    //Passed
    @Ignore
    @Test
    public void checkValueMoreTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 100);
        int secretNumber = model.setSecretValue();
        int inputMoreThanSecretNumber = secretNumber + 1;
        model.checkValue(inputMoreThanSecretNumber);
        int expected = inputMoreThanSecretNumber;
        int actual = model.getMaxBarrier();
        Assert.assertEquals(expected,actual);
    }

    //Passed
    @Ignore
    @Test
    public void checkValueLessTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 100);
        int secretNumber = model.setSecretValue();
        int inputLessThanSecretNumber = secretNumber - 1;
        model.checkValue(inputLessThanSecretNumber);
        int expected = inputLessThanSecretNumber;
        int actual = model.getMinBarrier();
        Assert.assertEquals(expected,actual);
    }

    //Passed
    @Ignore
    @Test
    public void checkValueWinTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 100);
        int secretNumber = model.setSecretValue();
        int WinTry = secretNumber;
        boolean result = model.checkValue(WinTry);
        Assert.assertFalse(result);
    }

    //Passed
    @Ignore
    @Test
    public void checkValueLoseTest() {
        Model model = new Model();
        model.setPrimaryBarrier(0, 100);
        int secretNumber = model.setSecretValue();
        int LoseTry = secretNumber + 1;
        boolean result = model.checkValue(LoseTry);
        Assert.assertTrue(result);
    }
}