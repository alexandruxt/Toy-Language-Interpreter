package Model.exp;

import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class ReadHeapExp extends Exp{
    Exp expression;

    public ReadHeapExp(Exp exp) {
        this.expression = exp;
    }

    @Override
    public IType typecheck(IDict<String, IType> Table) throws InterpreterException {
        IType typ = expression.typecheck(Table);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else
            throw new TypeException("The rH argument is not of Ref Type");
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws InterpreterException {
        IValue val = expression.eval(symTable,heapTable);
        if (val instanceof RefValue) {
            RefValue rv = (RefValue) val;
            int addr = rv.getAddress();
            if (heapTable.exists(addr)) {
                return heapTable.get(addr);
            } else {
                throw new InterpreterException("Not allocated heap");
            }
        } else {
            throw new InterpreterException("Expression not of refference type");
        }
    }


    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
