import java.io.FileNotFoundException;
import java.io.IOException;

public class Parent {
    // предок пугает IOException и InterruptedException
    public void f() throws IOException, InterruptedException {}
}

class Child extends Parent {
    // а потомок пугает только потомком IOException
    // потомок не может расширять сигнатуру только сужать
    @Override
    public void f() throws FileNotFoundException {}
}