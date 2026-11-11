package interpreter.model.stmt;


import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IHeap;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;


// wh() updates the value from the heap at the addres that `varName` points to with the value in `exp`

/*
new(v, 20); => heap { 1 -> 20 } (variable v pointer to address 1, which has value 20)
wH(v, 30); => heap { 1 -> 30 } ( updates )
new(v, 40); => heap {1 -> 30, 2 -> 40} (variable v pointer to address 2, which has value 40)
                                        value 30 at address 1 became Garbage ( we lost its pointer(s).)
 */
public record WriteHeapStmt(String varName, IExp exp) implements IStmt{     // wH(v, exp)

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IHeap heap = state.getHeap();
        IValue value;

        // 1. Check variable exists in symTable
        try {
            value = state.getSymTable().lookup(varName);
        }
        catch (ADTException e){
            throw new StatementException("Wh:" + e.getMessage());
        }

        //2. Check that the variable is a reference, then DOWNCAST
        if (! (value instanceof RefValue ref)){
            throw new StatementException("Wh: Value " + value.toString() + " is not a reference");
        }

        // 3. Check new value's type == location's type
        // NOTE:    In our ToyLanguage, the data type allocated to an address (aka `locationType`) is FINAL.
        //              You can only update the value, but not the type
        IValue newValue = exp.eval(state.getSymTable(), heap);

        if (!newValue.getType().equals(ref.locationType()))
            throw new StatementException("new: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());

        //4. Check that something is allocated in the heap at `address`
        int address = ref.address();
        if(! heap.isDefined(address)){
            throw new StatementException("Wh: Address " + address + " is not in the heap");
        }


        //5. Update value at `address`
        heap.update(address, newValue);

        return state;
    }

    @Override
    public String toString() {return "wH(" + varName + ", "+ exp.toString() + ")"; }
}
