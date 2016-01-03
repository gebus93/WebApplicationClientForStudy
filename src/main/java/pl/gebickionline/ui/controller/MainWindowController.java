package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class MainWindowController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
