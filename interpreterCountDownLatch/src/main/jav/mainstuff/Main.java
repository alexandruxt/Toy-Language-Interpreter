package mainstuff;

import Exceptions.InterpreterException;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import Repo.IRepo;
import Controller.Controller;
import Model.PrgState;
import View.ExitCmd;
import View.RunExemple;
import View.TextMenu;


public class Main {
    public static void main(String[] args) {
        PrgState state1, state2, state3, state4, state5, state6, state7;
        Controller ctrl1, ctrl2, ctrl3, ctrl4, ctrl5, ctrl6, ctrl7;
        try {
            IStmt ex7 = new CompStmt(
                    new VarDeclStmt(
                            "v",
                            new IntType()
                    ),
                    new CompStmt(
                            new VarDeclStmt(
                                    "a",
                                    new RefType(
                                            new IntType()
                                    )
                            ),
                            new CompStmt(
                                    new AssignStmt(
                                            "v",
                                            new ValueExp(
                                                    new IntValue(10)
                                            )
                                    ),
                                    new CompStmt(
                                            new AllocateHeapStmt(
                                                    "a",
                                                    new ValueExp(
                                                            new IntValue(22)
                                                    )
                                            ),
                                            new CompStmt(
                                                    new ForkStmt(
                                                            new CompStmt(
                                                                    new WriteHeapStmt(
                                                                            "a",
                                                                            new ValueExp(
                                                                                    new IntValue(30)
                                                                            )
                                                                    ),
                                                                    new CompStmt(
                                                                            new AssignStmt(
                                                                                    "v",
                                                                                    new ValueExp(
                                                                                            new IntValue(32)
                                                                                    )
                                                                            ),
                                                                            new CompStmt(
                                                                                    new PrintStmt(
                                                                                            new VarExp("v")
                                                                                    ),
                                                                                    new PrintStmt(
                                                                                            new ReadHeapExp(
                                                                                                    new VarExp(
                                                                                                            "a"
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompStmt(
                                                            new PrintStmt(
                                                                    new VarExp(
                                                                            "v"
                                                                    )
                                                            ),
                                                            new PrintStmt(
                                                                    new ReadHeapExp(
                                                                            new VarExp("a")
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            state7= new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex7);
            IRepo repo7 = new Repo("logExample7.txt");
            repo7.addPrg(state7);
            ctrl7 = new Controller(repo7);

            IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                    new ValueExp(new IntValue(4))), new CompStmt(new WhileStmt(new RelationalExpression(">",
                    new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")),
                    new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                    new PrintStmt(new VarExp("v")))));
            state6= new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex6);
            IRepo repo6 = new Repo("logExample6.txt");
            repo6.addPrg(state6);
            ctrl6 = new Controller(repo6);

            IStmt ex5 = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new AllocateHeapStmt(
                    "v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(
                    new RefType(new IntType()))), new CompStmt(new AllocateHeapStmt("a", new VarExp("v")),
                    new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ReadHeapExp(
                            new ReadHeapExp(new VarExp("a")))))))));
            state5 = new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex5);
            IRepo repo5 = new Repo("logExample5.txt");
            repo5.addPrg(state5);
            ctrl5 = new Controller(repo5);

            IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt
                    ("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                    new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                    new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                    new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
            state4 = new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex4);
            IRepo repo4 = new Repo("logExample4.txt");
            repo4.addPrg(state4);
            ctrl4 = new Controller(repo4);

            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                    new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                            VarExp("v"))))));
            state3 = new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex3);
            IRepo repo3 = new Repo("logExample3.txt");
            repo3.addPrg(state3);
            ctrl3 = new Controller(repo3);


            IStmt ex2 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))));
            state2 = new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex2);
            IRepo repo2 = new Repo("logExample2.txt");
            repo2.addPrg(state2);
            ctrl2 = new Controller(repo2);

            IStmt ex1 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                            new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                            new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                            new PrintStmt(new VarExp("b"))))));
            state1 = new PrgState(new LatchTable<>(), new Stack<>(), new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), ex1);
            IRepo repo1 = new Repo("logExample1.txt");
            repo1.addPrg(state1);
            ctrl1 = new Controller(repo1);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCmd("0", "exit"));
            menu.addCommand(new RunExemple("1", ex1.toString(), ctrl1));
            menu.addCommand(new RunExemple("2", ex2.toString(), ctrl2));
            menu.addCommand(new RunExemple("3", ex3.toString(), ctrl3));
            menu.addCommand(new RunExemple("4", ex4.toString(), ctrl4));
            menu.addCommand(new RunExemple("5", ex5.toString(), ctrl5));
            menu.addCommand(new RunExemple("6", ex6.toString(), ctrl6));
            menu.addCommand(new RunExemple("7", ex7.toString(), ctrl7));

            menu.show();
        } catch (InterpreterException exception) {
            System.out.println(exception.getMessage());
        }

    }
}


//        label:
//        while (true) {
//
//            System.out.println("\n0 - Exit\n");
//            System.out.println("1 - Run the first exemple\n");
//            System.out.println("2 - run the second exemple\n");
//            System.out.println("3 - Run the 3rd exemple\n");
//            Scanner s = new Scanner(System.in);
//            System.out.println("Enter the command:");
//            String cmd = s.next();
//            switch (cmd) {
//                case "0":
//                    System.out.println("Exit!");
//                    break label;
//                case "1": {
//                    IDict<String, IValue> symTable = new Dict<String, IValue>();
//                    IList<IValue> out = new List<IValue>();
//                    IStack<IStmt> exeStack = new Stack<>();
//                    PrgState myPrgState = new PrgState(exeStack, symTable, out, ex1);
//
//                    myController.addProgram(myPrgState);
//                    myController.allStep();
//                    break;
//                }
//                case "2": {
//                    IDict<String, IValue> symTable = new Dict<String, IValue>();
//                    IList<IValue> out = new List<IValue>();
//                    IStack<IStmt> exeStack = new Stack<>();
//                    PrgState myPrgState = new PrgState(exeStack, symTable, out, ex2);
//
//                    myController.addProgram(myPrgState);
//                    myController.allStep();
//                    break;
//                }
//                case "3": {
//                    IDict<String, IValue> symTable = new Dict<String, IValue>();
//                    IList<IValue> out = new List<IValue>();
//                    IStack<IStmt> exeStack = new Stack<>();
//                    PrgState myPrgState = new PrgState(exeStack, symTable, out, originalProgram1);
//
//                    myController.addProgram(myPrgState);
//                    myController.allStep();
//                    break;
//                }
//            }
//        }
//    }


