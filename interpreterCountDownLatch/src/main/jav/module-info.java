module interpreter.gui.mitrofan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports mainstuff;
    exports GUI;
    exports Controller;
    exports Exceptions;
    exports Model;
    exports Repo;
    exports View;
    opens GUI to javafx.fxml;
    opens mainstuff to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Exceptions to javafx.fxml;
    opens Model to javafx.fxml;
    opens Repo to javafx.fxml;
    opens View to javafx.fxml;
}