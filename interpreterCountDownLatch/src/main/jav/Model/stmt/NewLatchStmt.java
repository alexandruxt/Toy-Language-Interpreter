package Model.stmt;
import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILatchTable;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class NewLatchStmt implements IStmt{
    private static int newFreeLocation = -1;
    private final String var;
    private final Exp exp;

    public NewLatchStmt(String var,Exp exp) {
        this.var=var;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException{
        try {
            IDict<String, IValue> stbl = state.getSymTable();
            IValue num1 = this.exp.eval(stbl, state.getHeapTable());
            if (!num1.getType().equals(new IntType())) {
                throw new InterpreterException("Expression " + exp + " in statement " + this +
                        " does not evaluate to int.");
            }
            synchronized (state.getLatchTable()) {
                ++newFreeLocation;
                ILatchTable<Integer, IValue> latchTable = state.getLatchTable();
                latchTable.put(newFreeLocation, num1);
                if(state.getSymTable().isDefined(var) &&
                        state.getSymTable().lookup(var).getType().equals(new IntType())) {
                    stbl.update(var, new IntValue(newFreeLocation));
                    state.setSymTable(stbl);
                    state.setLatchTable(latchTable);
                }
                else
                    throw new InterpreterException("Variable " + var + " in statement " + this + " does not exist in symbol table" +
                        " or does not evaluate to int.");
                return null;
            }

        }
        catch(InterpreterException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType typeVariable = typeEnvironment.lookup(var);
        IType typeExpression = exp.typecheck(typeEnvironment);

        if(typeVariable.equals(new IntType()) && typeExpression.equals(new IntType()))
            return typeEnvironment;
        else
            throw new InterpreterException("Variable " + var + " or expression " + exp + " in " + this +
                    "do not evaluate to int.");
    }

    @Override
    public IStmt createCopy() {
        return new NewLatchStmt(var, exp);
    }
}
