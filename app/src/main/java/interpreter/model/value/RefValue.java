package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.RefType;

//"A reference value is a pair (address, locationType): the address of a heap location (an int) and the type of the value stored there"
//"The address 0 is invalid – it plays the role of null"

// = "AddresValue"
// = the address + what data type we'll find at that address

public record RefValue(int address, IType locationType) implements IValue {
    @Override public IType getType() { return new RefType(locationType); }
    @Override public String toString() {return "(" + address + ", " + locationType + ")";}
}