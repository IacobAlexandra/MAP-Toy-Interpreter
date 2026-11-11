package interpreter.model.exp;

/*  rh(v) == *v     (c++)

     cout << *v;    <==>      new PrintStmt(new ReadHeapExp(new VarExp("v")))
*/

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException{

        IValue value = exp.eval(symTable,heap);
        if(!(value instanceof RefValue ref)){   // DOWNCAST
            throw new ExpressionException("Rh: Value " + value.toString() + " is not a reference!");
        }

        int address = ref.address();

        try{
            return heap.get(address);
        }
        catch (ADTException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public String toString(){ return "rH(" + exp + ")";}
}

