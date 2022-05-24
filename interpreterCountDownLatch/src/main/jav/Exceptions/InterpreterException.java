package Exceptions;

public class InterpreterException  extends Exception{
    public InterpreterException(String msg) {super(msg);}

    @Override
    public String getMessage() {return super.getMessage();}
}
