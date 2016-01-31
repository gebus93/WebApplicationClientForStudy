package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pl.gebickionline.ui.Main;
import pl.gebickionline.ui.controller.news.NewsList;

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
        mainApp.setCenter(new NewsList());
    }

    @FXML
    private void goToPriceListManagement() {
        mainApp.changeView("PriceListViewer");
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
