package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public IType typecheck(IDict<String, IType> symTable) throws RuntimeException {
        return symTable.lookup(id);
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) {
        return symTable.lookup(id);
    }

    public String toString() {return id;}

}
