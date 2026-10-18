package interpreter.model.type;

import interpreter.model.value.IValue;

//"An IType knows its default value (rule S3). An IValue knows its type. "
public interface IType {
    IValue defaultValue();
}

