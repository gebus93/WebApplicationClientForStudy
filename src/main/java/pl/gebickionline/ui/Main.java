package pl.gebickionline.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.jetbrains.annotations.NotNull;
import pl.gebickionline.exception.ExceptionHandler;
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
                .setUncaughtExceptionHandler(new ExceptionHandler(this));


        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panel administracyjny");

        initRootLayout();
        showHomeView();
    }

    public void showHomeView() {
        @NotNull FXMLLoader loader = getFxmlLoader("HomeNew");
        BorderPane homeView;
        try {
            homeView = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        changeView(homeView);

    }

    private void changeView(Pane view) {
        rootLayout.setCenter(view);
    }

    public void showLoginForm() {

        FXMLLoader loader = getFxmlLoader("Login");
        AnchorPane login;
        try {
            login = loader.load();
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
            rootLayout = loader.load();
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


    public void showMainView() {

        try {
            FXMLLoader loader = getFxmlLoader("MainView");
            BorderPane view = loader.load();
            MainViewController controller = loader.getController();
            controller.setMainApp(this);
            changeView(view);

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
