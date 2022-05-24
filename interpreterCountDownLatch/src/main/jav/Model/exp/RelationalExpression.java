package Model.exp;

import Exceptions.InterpreterException;
import Exceptions.TypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExpression extends Exp{
    String operation;
    Exp expression1;
    Exp expression2;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Exp getExpression1() {
        return expression1;
    }


    public RelationalExpression(String operation, Exp expression1, Exp expression2) {
        this.operation = operation;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public IType typecheck(IDict<String, IType> Table) throws InterpreterException {
        IType t1, t2;
        t1 = expression1.typecheck(Table);
        t2 = expression2.typecheck(Table);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new TypeException("Second operand is not of int type");
            }
        } else  {
            throw new TypeException("First operand is not of int type");
        }
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws InterpreterException {
        IValue val1 = expression1.eval(symTable, heapTable);
        if (val1.getType().equals(new IntType())) {
            IValue val2 = expression2.eval(symTable, heapTable);
            if (val2.getType().equals(new IntType())) {
                IntValue intval1 = (IntValue)val1;
                IntValue intval2 = (IntValue)val2;
                int intv1 = intval1.getValue();
                int intv2 = intval2.getValue();
                return switch (operation){
                    case "<" -> new BoolValue(intv1 < intv2);
                    case "<=" -> new BoolValue(intv1 <= intv2);
                    case "==" -> new BoolValue(intv1 == intv2);
                    case "!=" -> new BoolValue(intv1 != intv2);
                    case ">" -> new BoolValue(intv1 > intv2);
                    case ">=" -> new BoolValue(intv1 >= intv2);
                    default -> throw new InterpreterException("Invalid operation");
                };
            } else {
                throw new InterpreterException("Expression 2 not of int type");
            }
        } else {
            throw new InterpreterException("Expression 1 not of int type");
        }
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
