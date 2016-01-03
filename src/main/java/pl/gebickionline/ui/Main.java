package pl.gebickionline.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pl.gebickionline.ui.controller.*;

import java.io.IOException;

/**
 * Created by Łukasz on 2015-11-28.
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Panel administracyjny");

        initRootLayout();

        showLoginForm();
    }

    private void showLoginForm() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            rootLayout.setCenter(login);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
            rootLayout = (BorderPane) loader.load();

            MainWindowController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/NewService.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            rootLayout.setCenter(login);
//
//            LoginController controller = loader.getController();
//            controller.setMainApp(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
