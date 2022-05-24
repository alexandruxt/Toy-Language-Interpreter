package Model.stmt;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILatchTable;
import Model.adt.IList;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

public class CountDownStmt implements IStmt{
    private final String variableName;

    public CountDownStmt(String var) {
        this.variableName = var;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        if(!(state.getSymTable().isDefined(variableName)
                && state.getSymTable().lookup(variableName).getType().equals(new IntType())))
        {
            throw new InterpreterException(variableName + " in " + this + " does not exist in symbol table or does not " +
                    "evaluate to int.");
        }
        IValue foundIndexValue = state.getSymTable().lookup(variableName);
        int foundIndex = ((IntValue)foundIndexValue).getValue();
        synchronized (state.getLatchTable()) {
            if(!state.getLatchTable().keys().contains(foundIndex))
                throw new InterpreterException(foundIndex + " in " + this + " is not existent in latchTable indexes.");
            if (((IntValue)state.getLatchTable().get(foundIndex)).getValue() > 0) {
                int val = ((IntValue) state.getLatchTable().get(foundIndex)).getValue();
                ILatchTable<Integer, IValue> ltbl = state.getLatchTable();
                ltbl.put(foundIndex, new IntValue(val - 1));
                state.setLatchTable(ltbl);
            }
        }
        IList<IValue> outt = state.getOut();
        outt.add(new StringValue(Integer.toString(state.getId())));
        state.setOut(outt);
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType typeVariable = typeEnvironment.lookup(variableName);
        if(typeVariable.equals(new IntType()))
            return typeEnvironment;
        else
            throw new InterpreterException("Variable " + variableName + " in " + this + " does not not have int type.");
    }

    @Override
    public IStmt createCopy() {
        return new CountDownStmt(variableName);
    }

    @Override
    public String toString() {
        return "countDown(" + this.variableName + ")";
    }
}