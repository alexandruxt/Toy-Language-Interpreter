package Repo;
import Exceptions.InterpreterException;
import Model.PrgState;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    void logPrgStateExec(PrgState newPrg) throws InterpreterException;
    //PrgState getCrtPrg();
    List<PrgState> getProgramList();
    void setProgramList(List<PrgState> programStateList);



}
