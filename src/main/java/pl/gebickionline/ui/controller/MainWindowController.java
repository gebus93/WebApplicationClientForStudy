package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import pl.gebickionline.security.AuthorizationProvider;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class MainWindowController {
    private Main mainApp;

    @FXML
    private MenuItem loginMenuItem;
    @FXML
    private MenuItem logoutMenuItem;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void logout() {
        AuthorizationProvider authorizationProvider = AuthorizationProvider.getInstance();
        if (authorizationProvider.isLoggedIn())
            authorizationProvider.logout();

        changeAuthorizationMenuText();
        mainApp.showHomeView();
    }


    @FXML
    private void login() {
        mainApp.showLoginForm();
    }

    public void changeAuthorizationMenuText() {
        if (AuthorizationProvider.getInstance().isLoggedIn()) {
            loginMenuItem.setVisible(false);
            logoutMenuItem.setVisible(true);
        } else {
            loginMenuItem.setVisible(true);
            logoutMenuItem.setVisible(false);
        }
    }
}
