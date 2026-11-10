package interpreter.model.type;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

//a Ref T variable gets (0, T);
// Ref(Ref(int)) equals Ref(Ref(int)) and differs from Ref(int)
public class RefTypeTest {



    @Test
    void refTypeDefaultValueIsZero() { // null == 0
        RefType type = new RefType(new IntType());

        assertEquals(new RefValue(0, new IntType()), type.defaultValue());
    }

    @Test
    void nestedReferenceTypesAreComparedCorrectly(){
        RefType singleRef = new RefType(new IntType());
        RefType doubleRef = new RefType(new RefType(new IntType()));

        //Ref(Ref(int)) equals Ref(Ref(int))
        assertEquals(new RefType(new RefType(new IntType())),doubleRef);

        //Ref(Ref(int)) differs from Ref(int)
        assertNotEquals(doubleRef,singleRef);
    }
}
