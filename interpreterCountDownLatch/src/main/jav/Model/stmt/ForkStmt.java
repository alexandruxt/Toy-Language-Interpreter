package Model.stmt;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.Stack;
import Model.types.IType;
import Model.value.IValue;

import java.util.Map;

public class ForkStmt implements IStmt{
    IStmt statement;

    public ForkStmt(IStmt stmt) {
        this.statement =stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        IDict<String, IValue> newStbl = new Dict<>();
        for (Map.Entry<String, IValue> entry : state.getSymTable().getContent().entrySet()) {
            newStbl.add(entry.getKey(), entry.getValue());
        }
        IStack<IStmt> stk = new Stack<>();
        stk.push(new NopStmt());
        stk.push(statement);
        PrgState newPRogram = new PrgState(state.getLatchTable(), stk, newStbl, state.getOut(), state.getFileTbl(), state.getHeapTable());
        newPRogram.setId();
        return newPRogram;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> tbl) throws InterpreterException {
        statement.typecheck(clone(tbl));
        return tbl;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    private static IDict<String, IType> clone(IDict<String, IType> tbl) throws InterpreterException {
        IDict<String, IType> newtbl = new Dict<>();
        for (Map.Entry<String, IType> entry : tbl.getContent().entrySet()) {
            newtbl.add(entry.getKey(), entry.getValue());
        }
        return newtbl;
    }

    @Override
    public IStmt createCopy() {
        return new ForkStmt(statement);
    }
}
