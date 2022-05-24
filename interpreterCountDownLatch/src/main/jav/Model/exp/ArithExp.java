package Model.exp;
import Exceptions.InterpreterException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp extends Exp{
  private  char op;
  private final Exp e1;
  private final Exp e2;

  public ArithExp(char operation, Exp expression1, Exp expression2){
      e1=expression1;
      e2=expression2;
      switch (operation) {
          case '+' -> this.op = '+';
          case '-' -> this.op = '-';

          case '*' -> this.op = '*';
          case '/' -> this.op = '/';
      }
  }

    @Override
    public IType typecheck(IDict<String, IType> symTable) throws InterpreterException {
         IType type1, type2;
         type1 = e1.typecheck(symTable);
         type2 = e2.typecheck(symTable);
         if (type1.equals(new IntType())){
             if (type2.equals(new IntType())){
                 return new IntType();
             }
             else{
                 throw new InterpreterException("Second operand is not an integer");
             }
         } else {
             throw new InterpreterException("First operand is not an integer");
         }
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTbl) throws InterpreterException {
        IValue val1, val2;
        val1 = e1.eval(symTable, heapTbl);
        if (val1.getType().equals(new IntType())) {
            val2 = e2.eval(symTable, heapTbl);
            if (val2.getType().equals(new IntType())) {
                IntValue intv1 = (IntValue) val1;
                IntValue intv2 = (IntValue) val2;
                int number1, number2;
                number1 = intv1.getValue();
                number2 = intv2.getValue();
                switch (op) {
                    case '+':
                        return new IntValue(number1 + number2);
                    case '-':
                        return new IntValue(number1 - number2);
                    case '*':
                        return new IntValue(number1 * number2);
                    case '/':
                        if (number2 == 0) throw new RuntimeException("Can't divide by 0");
                        return new IntValue(number1 / number2);
                    default:
                        throw new InterpreterException("Invalid operation!");
                }
            } else {
                throw new InterpreterException("Second operand is not an integer");
            }
        } else {
            throw new InterpreterException("First operand is not an integer");
        }
    }


    public int getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }
}
