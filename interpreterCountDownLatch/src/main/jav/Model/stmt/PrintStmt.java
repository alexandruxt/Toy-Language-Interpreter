package Model.stmt;


import Exceptions.InterpreterException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class PrintStmt implements IStmt{

    final Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    public PrgState execute(PrgState state) throws InterpreterException {
        IStack<IStmt> stk = state.getExeStack();
        IList<IValue> output = state.getOut();
        output.add(expression.eval(state.getSymTable(), state.getHeapTable()));
        state.setExeStack(stk);
        state.setOut(output);
        return null;
        //return state;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> tbl) throws InterpreterException {
        expression.typecheck(tbl);
        return tbl;
    }

    @Override
    public IStmt createCopy() {
        return new PrintStmt(expression);
    }
}
