package mainstuff;

import GUI.ChooseProgramController;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainGUI extends Application {

    ChooseProgramController chooseProgramController;

    @Override
    public void start(Stage primaryStage){
        try{
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/ChooseProgram.fxml"));
            GridPane root = fxmlLoader.load();
            chooseProgramController = fxmlLoader.getController();
            addStatementsToController();
            chooseProgramController.setMenuStage(primaryStage);
            Scene scene = new Scene(root, screenBounds.getWidth() /3, screenBounds.getHeight());
            primaryStage.setTitle("Toy Language Menu");
            primaryStage.setScene(scene);
            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addStatementsToController() {
        chooseProgramController.addStatement(example1);
        chooseProgramController.addStatement(example2);
        chooseProgramController.addStatement(example3);
        chooseProgramController.addStatement(example4);
        chooseProgramController.addStatement(example5);
        chooseProgramController.addStatement(example6);
        chooseProgramController.addStatement(example7);
        chooseProgramController.addStatement(example8);
    }

    private static final IStmt example1;
    static {
        example1 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
    }

    private static final IStmt example2;
    static {
        example2 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    private static final IStmt example3;
    static {
        example3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
    }

    private static final IStmt example4;
    static {
        example4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt
                ("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
    }

    private static final IStmt example5;
    static {
        example5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new AllocateHeapStmt(
                "v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(
                new RefType(new IntType()))), new CompStmt(new AllocateHeapStmt("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ReadHeapExp(
                        new ReadHeapExp(new VarExp("a")))))))));
    }

    private static final IStmt example6;
    static {
        example6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                new ValueExp(new IntValue(4))), new CompStmt(new WhileStmt(new RelationalExpression(">",
                new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")),
                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v")))));
    }

    private static final IStmt example7;
    static {
        example7 = new CompStmt(
                new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new AllocateHeapStmt("a",
                new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(
                new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
    }

    private static final IStmt example8;
    static {
        example8 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("cnt", new IntType()), new CompStmt(new AllocateHeapStmt("v1", new ValueExp(new IntValue(2))), new CompStmt(new AllocateHeapStmt("v2", new ValueExp(new IntValue(3))), new CompStmt(new AllocateHeapStmt("v3", new ValueExp(new IntValue(4))),
                         new CompStmt(new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new CountDownStmt("cnt")))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))), new CountDownStmt("cnt")))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("v3", new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v3"))), new CountDownStmt("cnt")))), new CompStmt(new AwaitStmt("cnt"), new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                        new CompStmt(new CountDownStmt("cnt"), new PrintStmt(new ValueExp(new IntValue(100)))))))))))))))));

    }



    public static void main(String[] args) {
        launch();
    }
}