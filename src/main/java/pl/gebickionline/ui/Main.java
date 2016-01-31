package pl.gebickionline.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.jetbrains.annotations.NotNull;
import pl.gebickionline.ui.controller.*;
import pl.gebickionline.ui.exception.ExceptionHandler;

import java.io.IOException;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainWindowController mainWindowController;
    private NavBar navBar = new NavBar();
    private static Main instance;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        Thread
                .currentThread()
                .setUncaughtExceptionHandler(new ExceptionHandler(this));
        primaryStage.getIcons().add(
                new Image(Main.class.getResourceAsStream("view/images/app-icon.png")));

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panel administracyjny");

        navBar.addLoginAction(this::showLoginForm);
        navBar.addLogoutAction(() -> mainWindowController.logout());
        navBar.addHomeButtonAction(this::showMainView);

        initRootLayout();
        showHomeView();

    }

    public static Main getInstance() {
        return instance;
    }

    public void showHomeView() {
        @NotNull FXMLLoader loader = getFxmlLoader("Home");
        BorderPane homeView;
        try {
            homeView = loader.load();
            navBar.showMenuForUnauthorized();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        changeView(homeView);

    }


    public void changeView(Pane view) {
        rootLayout.setCenter(view);
    }

    public void changeView(String viewName) {

        @NotNull FXMLLoader loader = getFxmlLoader(viewName);
        try {
            changeView(loader.<BorderPane>load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        dialogStage.getIcons().add(ImageLoader.load("login.png"));
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
            ((VBox) rootLayout.getTop()).getChildren().add(navBar);
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

            navBar.showMenuForAuthorized();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @NotNull
    public FXMLLoader getFxmlLoader(String viewName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/" + viewName + ".fxml"));
        return loader;
    }


    public void setCenter(Node node) {
        rootLayout.setCenter(node);
    }
}
