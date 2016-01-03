package pl.gebickionline.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.jetbrains.annotations.NotNull;
import pl.gebickionline.exception.AuthorizationException;
import pl.gebickionline.restclient.ExecuteRequestException;
import pl.gebickionline.ui.controller.*;

import java.io.IOException;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) {
        Thread
                .currentThread()
                .setUncaughtExceptionHandler((thread, throwable) -> {
                    if (throwable.getCause() != null && throwable.getCause().getCause() != null) {
                        if (throwable.getCause().getCause() instanceof ExecuteRequestException) {
                            showConnectionErrorAlert();
                            return;
                        } else if (throwable.getCause().getCause() instanceof AuthorizationException) {
                            showAuthorizationErrorAlert();
                            showLoginForm(mainWindowController);
                            return;
                        }
                    }

                    throwable.printStackTrace();
                });


        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panel administracyjny");

        initRootLayout();
        showHomeView();
    }

    public void showHomeView() {
        @NotNull FXMLLoader loader = getFxmlLoader("Home");
        AnchorPane homeView;
        try {
            homeView = (AnchorPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rootLayout.setCenter(homeView);

    }

    public void showLoginForm(MainWindowController mainWindowController) {

        FXMLLoader loader = getFxmlLoader("Login");
        AnchorPane login;
        try {
            login = (AnchorPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Logowanie do aplikacji");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(login);
        dialogStage.setScene(scene);
        LoginController controller = loader.getController();
        controller.addReferences(this, mainWindowController, dialogStage);
        dialogStage.showAndWait();
    }

    public void initRootLayout() {
        FXMLLoader loader = getFxmlLoader("MainWindow");
        try {
            rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainWindowController = loader.getController();
        mainWindowController.setMainApp(this);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Wystąpił nieoczekiwany błąd!");
        alert.setHeaderText("Błąd połączenia z serwerem!");
        alert.setContentText("Nastąpił nieoczekiwany błąd, podczas próby nawiązania połączenia z serwerem.\nSprawdź połączenie z internetem, lub upewnij się, że serwer jest uruchomiony.");
        alert.showAndWait();
    }

    private void showAuthorizationErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Wystąpił nieoczekiwany błąd!");
        alert.setHeaderText("Brak wymaganych uprawnień!");
        alert.setContentText("Nie posiadasz wystarczających uprawnień, aby uzyskać dostęp do wybranej metody.\n\nProszę się zalogować.");
        alert.showAndWait();
    }

    public void showMainView() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Podsumowanie autoryzacji!");
        alert.setHeaderText("Zalogowano");
        alert.setContentText("Logowanie powiodło się");
        alert.showAndWait();

        //------

        try {
            FXMLLoader loader = getFxmlLoader("NewService");
            AnchorPane login = (AnchorPane) loader.load();

            rootLayout.setCenter(login);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @NotNull
    private FXMLLoader getFxmlLoader(String viewName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/" + viewName + ".fxml"));
        return loader;
    }
}
