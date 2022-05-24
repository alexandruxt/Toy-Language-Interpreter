package Model.stmt;
import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    Exp expression;

    public OpenRFileStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        IStack<IStmt> stack = state.getExeStack();
        IDict<String, IValue> stbl = state.getSymTable();
        IDict<StringValue, BufferedReader> filetbl = state.getFileTbl();
        IValue val = expression.eval(stbl, state.getHeapTable());
        if (val.getType().equals(new StringType())) {
            StringValue strVal = (StringValue) val;
            if (filetbl.isDefined(strVal)) {
                throw new InterpreterException("File already opened");
            } else {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(strVal.getValue()));
                    filetbl.add(strVal, br);
                } catch (IOException exception) {
                    throw new InterpreterException("File cannot be opened " + exception.getMessage());
                }
            }
        } else {
            throw new InterpreterException("Expression not of string type");
        }
        state.setExeStack(stack);
        state.setSymTable(stbl);
        state.setFileTbl(filetbl);
        return null;
        // return state;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> tbl) throws InterpreterException {
        IType type = expression.typecheck(tbl);
        if (type.equals(new StringType()))
            return tbl;
        else
            throw new TypeException("Expression not of string type");

    }

    @Override
    public String toString() {return "open(" + expression.toString() + ")";}

    @Override
    public IStmt createCopy() {
        return new OpenRFileStmt(expression);
    }
}
