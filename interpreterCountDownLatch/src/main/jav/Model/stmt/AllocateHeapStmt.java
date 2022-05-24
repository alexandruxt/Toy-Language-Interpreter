package Model.stmt;

import Exceptions.AssignmentException;
import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class AllocateHeapStmt implements IStmt{
    String varName;
    Exp expression;

    public AllocateHeapStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        IStack<IStmt> stack = state.getExeStack();
        IDict<String, IValue> symbolTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        if (symbolTable.isDefined(varName)) {
            if (symbolTable.lookup(varName).getType() instanceof RefType) {
                IValue val = expression.eval(symbolTable, heapTable);
                IValue tblVal = symbolTable.lookup(varName);
                if (val.getType().equals(((RefType)(tblVal.getType())).getInner())) {
                    int addr = heapTable.allocate(val);
                    symbolTable.update(varName,new RefValue(addr,val.getType()));
                } else {
                    throw new AssignmentException("alue not of correct type");
                }
            } else {
                throw new AssignmentException("Variable not of reference type");
            }
        } else {
            throw new AssignmentException("Variable not declared");
        }
        state.setHeapTable(heapTable);
        state.setSymTable(symbolTable);
        state.setExeStack(stack);
        return null;
        //return state;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> tbl) throws InterpreterException {
        IType vart = tbl.lookup(varName);
        IType expt = expression.typecheck(tbl);
        if (vart.equals(new RefType(expt)))
            return tbl;
        else
            throw new TypeException("Different types on heap allocation");
    }

    @Override
    public String toString() {
        return "new(" + varName + ',' + expression + ')';
    }

    @Override
    public IStmt createCopy() {
        return new AllocateHeapStmt(varName, expression);
    }
}
