package GUI;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.PrgState;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.stmt.NopStmt;
import Model.value.IValue;
import Repo.IRepo;
import Repo.Repo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainProgramController {

    private int currentState = 1;
    private Controller controller;
    java.util.List<PrgState> prgStateList;

    @FXML
    private ListView<String> fileTblListView;
    @FXML
    private ListView<String> outputConsoleListView;
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private ListView<String> prgStatesListView;
    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressTblCol;
    @FXML
    private TextField exeStackTextField;
    @FXML
    private TableView<Pair<String, IValue>> symTblView;
    @FXML
    private TableView<Pair<Integer, IValue>> latchTblView;
    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> locationTblCol;
    @FXML
    private TableColumn<Pair<Integer, IValue>, IValue> valueTblCol;
    @FXML
    private TableColumn<Pair<String, IValue>, String> varNameTblCol;
    @FXML
    private TableColumn<Pair<Integer, IValue>, IValue> varValTblCol;
    @FXML
    private TextField symTblTextField;
    @FXML
    private TableColumn<Pair<Integer, IValue>, IValue> valTblCol;
    @FXML
    private TableView<Pair<Integer, IValue>> heapTblView;
    @FXML
    private TextField nrOfStatesTextFields;

    public void setPrgStates(IStmt stmt, int index) throws InterpreterException {
        String file = "LogExample" + index + ".txt";
        IStack<IStmt> stack = new Stack<>();
        stack.push(new NopStmt());
        PrgState state = new PrgState(new LatchTable<>(), stack, new Dict<>(), new List<>(), new Dict<>(), new Heap<>(), stmt);
        IRepo repo = new Repo(file);
        repo.addPrg(state);
        controller = new Controller(repo);
        controller.openExecutor();
        prgStateList = controller.getStateList();
    }

    private void raiseAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    private void oneStep() {
        try {
            controller.conservativeGarbageCollector(prgStateList);
            controller.oneStepForAllPrg(prgStateList);
            prgStateList = controller.getStateList();
            if (prgStateList.size() == 0) {
                controller.closeExecutor();
                controller.setFinalStateList(prgStateList);
            }
        } catch (InterpreterException e) {
            raiseAlert("Interpreter: " + e.getMessage());
        } catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }

    private void populateHeapTable() {
        java.util.List<Pair<Integer, IValue>> list = new LinkedList<>();
        controller.getHeapTable().getContent().forEach((key, value) -> list.add(new Pair<>(key, value)));
        heapTblView.setItems(FXCollections.observableList(list));
    }

    private void populateLatchTable() {
        java.util.List<Pair<Integer, IValue>> list = new LinkedList<>();
        controller.getLatchTable().getContent().forEach((key, value) -> list.add(new Pair<>(key, value)));
        latchTblView.setItems(FXCollections.observableList(list));
    }

    private void populateExecutionList() {
        exeStackTextField.setText("Execution Stack for " + currentState);
        java.util.List<String> list = controller.getExeStack(currentState).getContent()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        Collections.reverse(list);
        exeStackListView.setItems(FXCollections.observableList(list));
    }

    public void populateSymbolTable() {
        symTblTextField.setText("Symbol Table for " + currentState);
        java.util.List<Pair<String, IValue>> list = new LinkedList<>();
        controller.getSymTable(currentState)
                .getContent()
                .forEach((key, value) -> list.add(new Pair<>(key,value)));
        symTblView.setItems(FXCollections.observableList(list));
    }

    public void populateProgramList() {
        java.util.List<String> list = new LinkedList<>();
        controller.getStateList().forEach((el) -> list.add(el.getId().toString()));
        prgStatesListView.setItems(FXCollections.observableList(list));
    }

    public void populateFileList() {
        java.util.List<String> list = new LinkedList<>();
        controller.getFileTable().getContent().forEach((key, value) -> list.add(key.getValue()));
        fileTblListView.setItems(FXCollections.observableList(list));
    }

    public void populateOutputList() {
        java.util.List<String> list = controller.getOutput().getContent().stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        outputConsoleListView.setItems(FXCollections.observableList(list));
    }

    private void setNrOfStates() {
        int nrOfStates = controller.nrOfPrgStates();
        if (nrOfStates == 0) {
            nrOfStatesTextFields.setText("There are no program states!");
        } else if (nrOfStates == 1) {
            nrOfStatesTextFields.setText("There is 1 program state!");
        } else {
            nrOfStatesTextFields.setText("There are " + nrOfStates + " program states!");
        }
    }

    private void setAllFields() {
        populateHeapTable();
        populateFileList();
        populateOutputList();
        populateProgramList();
        populateSymbolTable();
        populateLatchTable();
        populateExecutionList();
        setNrOfStates();
    }

    @FXML
    private void runOneStep() {
        oneStep();
        setAllFields();
    }

    @FXML
    private void initialize() {
        heapTblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        latchTblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        symTblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addressTblCol.setCellValueFactory(new PairKeyFactory<>());
        valTblCol.setCellValueFactory(new PairValueFactory<>());
        locationTblCol.setCellValueFactory(new PairKeyFactory<>());
        valueTblCol.setCellValueFactory(new PairValueFactory<>());
        varNameTblCol.setCellValueFactory(new PairKeyFactory<>());
        varValTblCol.setCellValueFactory(new PairValueFactory<>());
        prgStatesListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            try {
                currentState = Integer.parseInt(t1);
            } catch (Exception ignored) {
                return;
            }
            if (currentState == 0){
                return;
            }
            populateSymbolTable();
            populateExecutionList();
        }));

    }

}

class PairKeyFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, T>, ObservableValue<T>> {

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<Pair<T, E>, T> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}

class PairValueFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, E>, ObservableValue<E>> {

    @Override
    public ObservableValue<E> call(TableColumn.CellDataFeatures<Pair<T, E>, E> data) {
        E value = data.getValue().getValue();
        if (value instanceof ObservableValue){
            return (ObservableValue) value;
        } else {
            return new ReadOnlyObjectWrapper<>(value);
        }

    }
}