package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException;
    //NOTE - symtable: we pass the symbol table to resolve the variables
    //NOTE - heap: rH reads the heap, so an expression needs the heap to be evaluated


}
