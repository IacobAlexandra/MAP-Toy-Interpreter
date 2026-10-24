package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// to test: stack: LIFO order, pop on an empty stack throws
public class MyStackTest {

    @Test
    void lifoOrderStack() throws ADTException{
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1); stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1,stack.pop());
        assertEquals(true, stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrows() {
        MyStack<Integer> stack = new MyStack<>();
        assertThrows(ADTException.class, () -> stack.pop());
    }
}
