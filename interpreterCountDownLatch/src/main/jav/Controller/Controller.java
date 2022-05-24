package Controller;
import Exceptions.InterpreterException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Model.value.StringValue;
import Repo.IRepo;
import Model.PrgState;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    final IRepo repo;
    ExecutorService executor;
    //boolean displayAll;

    public Controller(IRepo repo) {
        this.repo = repo;
        //this.displayAll = false;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTblAddr, Map<Integer, IValue>heap) {
        return heap.entrySet().stream()
                .filter(e -> symTblAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTbl(Collection<IValue> symTblVals, Collection<IValue> heapTbl) {
        return Stream.concat(
                heapTbl.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v;
                            return v1.getAddress();}),
                symTblVals.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v;
                    return v1.getAddress();})
        ).collect(Collectors.toList());
    }

    public void conservativeGarbageCollector(List<PrgState> prgStateList) {
        var heap = Objects.requireNonNull(prgStateList.stream()
                .map(p -> getAddrFromSymTbl(
                        p.getSymTable().getContent().values(),
                        p.getHeapTable().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());
        prgStateList.forEach(prgState -> {
            prgState.getHeapTable().setContent(
                    safeGarbageCollector(
                            heap,
                            prgStateList.get(0).getHeapTable().getContent()
                    ));
        });
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterpreterException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (InterpreterException e) {
                e.printStackTrace();
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        try {
            List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgList.addAll(newProgramList);
        } catch (InterruptedException e) {
            throw new InterpreterException(e.getMessage());
        }

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (InterpreterException e) {
                e.printStackTrace();
            }
        });
        repo.setProgramList(prgList);
    }

    public void allStep() throws InterpreterException{
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getProgramList());
        while (prgList.size() > 0) {
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getProgramList());
        }
        executor.shutdownNow();
        repo.setProgramList(prgList);
    }
//    public PrgState oneStep(PrgState state) throws InterpreterException {
//        IStack<IStmt> stk = state.getExeStack();
//        if (stk.isEmpty()) {
//           // displayAll = false;
//            throw new InterpreterException("Prg state is empty!");
//        }
//        //else
//        //    displayAll = true;
//        IStmt crtStmt = stk.pop();
//        return crtStmt.execute(state);
//    }

//    public void allStep() throws InterpreterException{
//        PrgState prg = repo.getCrtPrg();
//        repo.logPrgStateExec(prg);
////        if (displayAll)
////            System.out.println(displayState(prg));
//        while (!prg.getExeStack().isEmpty()) {
//            try {
//                PrgState prg1 = oneStep(prg);
//                repo.logPrgStateExec(prg1);
//                prg.getHeapTable().setContent(unsafeGarbageCollector(
//                        getAddrFromSymTbl(prg.getSymTable().getContent().values()),
//                                prg.getHeapTable().getContent()));
////                if (displayAll)
////                    System.out.println(displayState(prg));
//            } catch (InterpreterException e){
//                throw new InterpreterException(e.getMessage());
//            }
//        }
//        //System.out.println(getOutput(prg));
//    }

//    public void setDisplayAll(Boolean displayAll) {
//        this.displayAll = displayAll;
//    }

    public Integer nrOfPrgStates() {
        return repo.getProgramList().size();
    }

    public void openExecutor() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void closeExecutor() {
        executor.shutdownNow();
    }

    public String displayState(PrgState state) {
        return state.toString();
    }

    public IList<IValue> getOutput() {
        if (repo.getProgramList().size() == 0) {
            return new Model.adt.List<>();
        }
        return repo.getProgramList().get(0).getOut();
    }

    public IHeap<IValue> getHeapTable() {
        if (repo.getProgramList().size() == 0) {
            return new Model.adt.Heap<>();
        }
        return repo.getProgramList().get(0).getHeapTable();
    }

    public ILatchTable<Integer, IValue> getLatchTable() {
        if (repo.getProgramList().size() == 0) {
            return new Model.adt.LatchTable<>();
        }
        return repo.getProgramList().get(0).getLatchTable();
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        if (repo.getProgramList().size() == 0) {
            return new Model.adt.Dict<>();
        }
        return repo.getProgramList().get(0).getFileTbl();
    }

    public IDict<String, IValue> getSymTable(int process) {
        List<PrgState> states = repo.getProgramList().stream()
                .filter((el) -> el.getId() == process)
                .collect(Collectors.toList());
        if (states.size() == 0) {
            return new Model.adt.Dict<>();
        }
        return states.get(0).getSymTable();
    }

    public IStack<IStmt> getExeStack(int process) {
        List<PrgState> states = repo.getProgramList().stream()
                .filter((el) -> el.getId() == process)
                .collect(Collectors.toList());
        if (states.size() == 0) {
            return new Model.adt.Stack<>();
        }
        return states.get(0).getExeStack();
    }

    public void setFinalStateList(List<PrgState> prgStateList) {
        repo.setProgramList(prgStateList);
    }

    public void typeCheck() throws InterpreterException {
        repo.getProgramList().get(0).typeCheck();
    }

    public List<PrgState> getStateList() {
        return removeCompletedPrg(repo.getProgramList());
    }
}
