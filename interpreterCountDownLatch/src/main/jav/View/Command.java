package View;

public abstract class Command {
    private final String key;
    private final String description;
    public Command(String ky, String descr) {this.key = ky; this.description = descr;}
    public abstract void execute();
    public String getKey() {return this.key;}
    public String getDescription() {return this.description;}
}
