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
import java.io.IOException;

public class CloseRFile implements IStmt{
    Exp expression;

    public CloseRFile(Exp expr) {
        this.expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        IStack<IStmt> stack = state.getExeStack();
        IDict<String, IValue> stbl = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTbl = state.getFileTbl();
        IValue val = expression.eval(stbl, state.getHeapTable());
        if (val.getType().equals(new StringType())) {
            StringValue strVal = (StringValue) val;
            if (fileTbl.isDefined(strVal)) {
                BufferedReader br = fileTbl.lookup(strVal);
                try {
                    br.close();
                } catch (IOException exception) {
                    throw new InterpreterException("Could not close the file " + exception.getMessage());
                }
                fileTbl.remove(strVal);
            } else {
                throw new InterpreterException("File to close does not exist");
            }
        } else {
            throw new InterpreterException("Filename is not a string");
        }
        state.setExeStack(stack);
        state.setSymTable(stbl);
        state.setFileTbl(fileTbl);
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
    public String toString() {
        return "close(" + expression.toString() + ")";
    }

    @Override
    public IStmt createCopy() {
        return new CloseRFile(expression);
    }
}
