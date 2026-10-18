package interpreter.model.value;

import interpreter.model.type.*;

public record BoolValue(boolean val) implements IValue {
    @Override public IType getType() { return new BoolType(); }
    @Override public String toString() { return Boolean.toString(val); }
}
