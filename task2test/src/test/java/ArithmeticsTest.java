import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;


public class ArithmeticsTest {
    private static Arithmetics arithmetics;

    @Rule
    public final ExpectedException exp = ExpectedException.none();

    @Rule
    public final Timeout time = new Timeout(1000);

    @BeforeClass
    public static void initialize() {
        arithmetics = new Arithmetics();
    }

    @Test
    public void testAdd() {
        double res = arithmetics.add(5, 8);
        Assert.assertEquals(13, res, 0.0);
    }

    @Test
    public void testDeduct() {
        double res = arithmetics.deduct(13, 17);
        Assert.assertEquals(-4, res, 0.0);
    }

    @Test
    public void testMult() {
        double res = arithmetics.mult(5, 7);
        Assert.assertEquals(35, res, 0.0);
    }

    @Test
    public void testDiv() {
        double res = arithmetics.div(30, 5);
        Assert.assertEquals(6, res, 0.0);
    }

//    @Test(expected = ArithmeticException.class)
    @Test
    public void testDivException() {
        exp.expect(ArithmeticException.class);
        arithmetics.div(30, 0);
    }

    @Test
    public void testN() {
//        while(true){}
    }
}