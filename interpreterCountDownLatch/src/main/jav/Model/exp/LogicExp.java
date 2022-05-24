package Model.exp;

import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicExp extends Exp {
    final char op;
    final Exp e1;
    final Exp e2;

    @Override
    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }

    public LogicExp(Exp expression1, Exp expression2, char operation){
        this.e1 = expression1;
        this.e2 = expression2;
        this.op = operation;
    }

    @Override
    public IType typecheck(IDict<String, IType> symTable) throws InterpreterException {
        IType type1, type2;
        type1 = e1.typecheck(symTable);
        type2 = e2.typecheck(symTable);
        if (type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new InterpreterException("Second operand is not a boolean");
            }
        } else {
            throw new InterpreterException("First operand is not a boolean");
        }
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws InterpreterException {
        IValue val1, val2;
        val1 = e1.eval(symTable, heapTable);
        if (val1.getType().equals(new BoolType())) {
            val2 = e2.eval(symTable, heapTable);
            if (val2.getType().equals(new BoolType())) {
                BoolValue bv1 = (BoolValue) val1;
                BoolValue bv2 = (BoolValue) val2;
                Boolean bool1 = bv1.getValue();
                Boolean bool2 = bv2.getValue();
                switch (op) {
                    case '&':
                        return new BoolValue(bool1 & bool2);
                    case '|':
                        return new BoolValue(bool1 | bool2);
                    default:
                        throw new InterpreterException("Invalid operation!");
                }
            } else {
                throw new TypeException("Second operand is not a boolean");
            }
        } else {
            throw new TypeException("First operand is not a boolean");
        }

    }
}
