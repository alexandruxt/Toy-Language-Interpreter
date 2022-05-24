package Model.stmt;

import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

import java.util.Map;

public class WhileStmt implements IStmt {
    Exp expression;
    IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        IStack<IStmt> stk = state.getExeStack();
        IValue expVal = expression.eval(state.getSymTable(), state.getHeapTable());
        if (expVal.getType().equals(new BoolType())) {
            if (expVal.equals(new BoolValue(true))) {
                stk.push(new WhileStmt(expression,statement));
                stk.push(statement);
            }
        } else {
            throw new TypeException("Expression not of type bool");
        }
        state.setExeStack(stk);
        //return state;
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> tbl) throws InterpreterException {
        IType expt = expression.typecheck(tbl);
        if (expt.equals(new BoolType())){
            statement.typecheck(clone(tbl));
            return tbl;
        } else
            throw new TypeException("Condition not of type bool");
    }

    private static IDict<String, IType> clone(IDict<String, IType> table) throws InterpreterException {
        IDict<String, IType> newSymbolTable = new Dict<>();
        for (Map.Entry<String, IType> entry: table.getContent().entrySet()) {
            newSymbolTable.add(entry.getKey(), entry.getValue());
        }
        return newSymbolTable;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") { " + statement.toString() + "}";
    }

    @Override
    public IStmt createCopy() {
        return new WhileStmt(expression,statement);
    }
}
