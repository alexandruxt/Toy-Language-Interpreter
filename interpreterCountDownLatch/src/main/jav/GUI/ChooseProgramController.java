package GUI;

import Exceptions.InterpreterException;
import Model.adt.Dict;
import Model.stmt.IStmt;
import Exceptions.InterpreterException;
import Model.adt.Dict;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChooseProgramController {

    private final List<IStmt> statements = new LinkedList<>();
    private Stage menuStage;

    @FXML
    private ListView<String> programStateListView;

    @FXML
    private void initialize() {
        programStateListView.setItems(getProgramStatesList());
    }

    public void setMenuStage(Stage stage) {
        menuStage = stage;
    }

    public void addStatement(IStmt stmt) {
        statements.add(stmt);
        programStateListView.setItems(getProgramStatesList());
    }

    private ObservableList<String> getProgramStatesList() {
        return FXCollections.observableList(statements.stream()
                .map(Objects::toString)
                .collect(Collectors.toList()));
    }

    private void launchNewWindow(int prgNr) throws IOException, InterpreterException {
        IStmt stmt = statements.get(prgNr - 1);
        stmt.typecheck(new Dict<>());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainProgram.fxml"));
        GridPane secondaryLayout = loader.load();
        MainProgramController mainProgramController = loader.getController();
        mainProgramController.setPrgStates(stmt, prgNr);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene secondScene = new Scene(secondaryLayout, screenBounds.getWidth() * 2 / 3, screenBounds.getHeight() - 50);

        Stage newWindow = new Stage();
        newWindow.setTitle("Program " + prgNr);
        newWindow.setScene(secondScene);
        newWindow.setX(menuStage.getX() + screenBounds.getWidth() /3);
        newWindow.setY(menuStage.getY());
        newWindow.show();
    }

    public void raiseAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    public void selectProgram() {
        try {
            int index = programStateListView.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                raiseAlert("Please select a program");
                return;
            }
            launchNewWindow(index + 1);
        } catch (InterpreterException e) {
            raiseAlert(e.getMessage());
        } catch (Exception e) {
            raiseAlert(e.getMessage());
            e.printStackTrace();
        }
    }
}
