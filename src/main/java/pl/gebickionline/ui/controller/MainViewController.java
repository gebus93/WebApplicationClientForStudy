package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class MainViewController {
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void goToNewsManagement() {
        showAlert("Aktualności");
    }

    @FXML
    private void goToPriceListManagement() {
        showAlert("Cennik");
    }

    @FXML
    private void goToContactManagement() {
        mainApp.changeView("ContactDataEditor");
    }

    private void showAlert(String pageName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Zmiana strony");
        alert.setContentText("Nowa strona to " + pageName);
        alert.showAndWait();

    }
}
