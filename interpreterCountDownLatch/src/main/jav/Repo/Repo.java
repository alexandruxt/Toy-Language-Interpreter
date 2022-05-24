package Repo;
import Exceptions.InterpreterException;
import Model.PrgState;

import  java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    String logFilePath;

    public Repo(String lg) throws InterpreterException {
        try {
            PrintWriter emptyFile = new PrintWriter(new BufferedWriter(new FileWriter(lg)));
        } catch (IOException exception) {
            throw new InterpreterException("Log file could not be opened");
        }
        myPrgStates = new LinkedList<>();
        logFilePath = lg;
    }

//    @Override
//    public PrgState getCrtPrg() {
//        PrgState curr = myPrgStates.get(0);
//        myPrgStates.remove(0);
//        return curr;
//    }

    @Override
    public List<PrgState> getProgramList() {
        return myPrgStates;
    }

    @Override
    public void setProgramList(List<PrgState> programStateList) {
        this.myPrgStates = programStateList;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec(PrgState newPrg) throws InterpreterException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException exception) {
            throw new InterpreterException(exception.getMessage());
        }
        logFile.println(newPrg.toString());
        logFile.flush();
        if (newPrg.getExeStack().isEmpty()) {
            logFile.close();
            //myPrgStates.remove(0);
        }

    }
}
