package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    //NOTE: We use List, not IList

    //NOTE: final means we cant reassign the object,
    // but, yeah, we can still do stuff inside it
    private final List<PrgState> programs = new ArrayList<>();


    public Repository(PrgState state){
        this.programs.add(state);
    }

    @Override
    //For A2 we have only one programstate, so we just hardcode position 0
    public PrgState getCrtPrg() { return programs.get(0);}
}
