package pl.gebickionline.ui.controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Created by Łukasz on 2016-01-30.
 */
public class NavBar extends HBox {

    private static final int NAV_BAR_HEIGHT = 100;
    private final VBox homeButton;
    private final VBox logoutButton;
    private final VBox loginButton;
    private final VBox exitButton;

    public NavBar() {
        getStyleClass().add("nav-bar");
        setMaxHeight(NAV_BAR_HEIGHT);
        setPrefHeight(NAV_BAR_HEIGHT);
        setMinHeight(NAV_BAR_HEIGHT);

        homeButton = getButton("Panel domowy", "home.png");
        setWidth(homeButton, NAV_BAR_HEIGHT);
        logoutButton = getButton("Wyloguj", "logout.png");
        loginButton = getButton("Zaloguj", "login.png");
        exitButton = getButton("Zamknij program", "exit.png");
        exitButton.setOnMouseClicked(event -> System.exit(0));
        setWidth(exitButton, NAV_BAR_HEIGHT + 20);
    }

    private VBox getButton(String title, String iconPath) {
        VBox layout = new VBox();
        layout.getStyleClass().add("nav-bar-button");
        setHeight(layout, NAV_BAR_HEIGHT - 20);
        setWidth(layout, NAV_BAR_HEIGHT - 20);

        setMargin(layout, new Insets(10, 15, 10, 15));

        ImageView icon = new ImageView(ImageLoader.load(iconPath));
        icon.setFitHeight(50);
        icon.setFitWidth(50);

        Label titleLabel = new Label(title);
        titleLabel.setMaxHeight(Double.MAX_VALUE);

        layout.getChildren().addAll(icon, titleLabel);
        return layout;
    }

    private void setHeight(VBox layout, int height) {
        layout.setMinHeight(height);
        layout.setMaxHeight(height);
        layout.setPrefHeight(height);
    }

    private void setWidth(VBox layout, int width) {
        layout.setMinWidth(width);
        layout.setMaxWidth(width);
        layout.setPrefWidth(width);
    }

    public void showMenuForUnauthorized() {
        getChildren().clear();
        getChildren().addAll(loginButton, exitButton);
    }

    public void showMenuForAuthorized() {
        getChildren().clear();
        getChildren().addAll(homeButton, logoutButton, exitButton);
    }

    public void addLoginAction(Runnable runnable) {
        loginButton.setOnMouseClicked(event -> runnable.run());
    }

    public void addLogoutAction(Runnable runnable) {
        logoutButton.setOnMouseClicked(event -> runnable.run());
    }

    public void addHomeButtonAction(Runnable runnable) {
        homeButton.setOnMouseClicked(event -> runnable.run());
    }
}
