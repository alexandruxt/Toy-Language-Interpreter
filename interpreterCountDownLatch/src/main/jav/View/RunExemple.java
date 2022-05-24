package View;
import Controller.Controller;
import Exceptions.InterpreterException;
import Exceptions.TypeException;

public class RunExemple extends Command {
    private final Controller ctr;

    public RunExemple(String ky, String descr, Controller ctrl) {
        super(ky, descr);
        this.ctr = ctrl;
    }

    @Override
    public void execute() {
        try {
            ctr.typeCheck();
            ctr.allStep();
        } catch (InterpreterException exception) {
            if (exception instanceof TypeException) {
                System.out.println("TypeChecking failed: " + exception.getMessage());
            } else {
                System.out.println(exception.getMessage());
            }
        }
    }
}
