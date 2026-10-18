package interpreter.model.type;

import interpreter.model.value.*;

public record BoolType() implements IType {
    @Override public IValue defaultValue() { return new BoolValue(false); }
    @Override public String toString() { return "bool"; }
}
