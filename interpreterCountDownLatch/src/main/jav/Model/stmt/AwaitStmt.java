package Model.stmt;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class AwaitStmt implements IStmt {
    private final String variableName;

    public AwaitStmt(String var){
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

        if(!state.getLatchTable().keys().contains(foundIndex))
            throw new InterpreterException(foundIndex + " in " + this + " is not existent in latchTable.");

        if(state.getLatchTable().get(foundIndex).equals(new IntValue(0)))
            return null;

        else state.getExeStack().push(this);
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
        return new AwaitStmt(variableName);
    }

    @Override
    public String toString(){
        return "await(" + variableName + ")";
    }
}