package View;

public class ExitCmd extends Command {
    public ExitCmd(String ky, String descr) {
        super(ky, descr);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
