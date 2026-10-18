package interpreter.model.value;
import interpreter.model.type.IType;

//"An IType knows its default value (rule S3). An IValue knows its type. "
public interface IValue {
    IType getType();
}

//OBS: Records give equals, hashCode and toString for free, so val.getType().equals(new IntType()) works without any extra code