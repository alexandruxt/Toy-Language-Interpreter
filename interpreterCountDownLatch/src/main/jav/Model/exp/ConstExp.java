package Model.exp;
import Exceptions.InterpreterException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;
import Model.value.IntValue;

public class ConstExp extends Exp{
    IntValue number;

    public ConstExp(IntValue number) {
        this.number = number;
    }

    @Override
    public IType typecheck(IDict<String, IType> symTable) throws InterpreterException {
        return number.getType();
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws InterpreterException {
        return this.number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
