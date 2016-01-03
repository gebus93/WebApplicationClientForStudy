package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.gebickionline.security.AuthorizationProvider;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-03.
 */
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    private Main mainApp;
    private MainWindowController mainWindowController;
    private Stage dialogStage;
    private AuthorizationProvider authorizationProvider;

    @FXML
    private void initialize() {
        authorizationProvider = AuthorizationProvider.getInstance();
    }

    @FXML
    private void logIn() {

        usernameField.getText();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showErrorAlert("Aby dokonać autoryzacji należy podać zarówno nazwę użytkownika, jak i hasło.");
            return;
        }

        authorizationProvider.login(usernameField.getText(), passwordField.getText());

        if (authorizationProvider.isLoggedIn()) {
            mainWindowController.changeAuthorizationMenuText();
            mainApp.showMainView();
            dialogStage.close();
        } else {
            showErrorAlert("Autoryzacja nie powiodła się.\nNajprawdopodobniej podana nazwa użytkownika, lub hasło są błędne.\nProszę spróbować ponownie.");
        }
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Błąd podczas logowania!");
        alert.setHeaderText("Nieprawidłowe dane logowania");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void addReferences(Main mainApp, MainWindowController mainWindowController, Stage dialogStage) {
        this.mainApp = mainApp;
        this.mainWindowController = mainWindowController;
        this.dialogStage = dialogStage;
    }
}
