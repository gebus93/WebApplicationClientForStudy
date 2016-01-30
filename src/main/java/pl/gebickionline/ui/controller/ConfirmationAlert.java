package pl.gebickionline.ui.controller;

import javafx.scene.control.*;

/**
 * Created by Łukasz on 2016-01-30.
 */
public class ConfirmationAlert extends Alert {
    private ButtonType confirmButton = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);


    public ConfirmationAlert(String title, String text) {
        super(AlertType.CONFIRMATION);
        getDialogPane().getButtonTypes().clear();
        getDialogPane().getButtonTypes().addAll(confirmButton, cancel);
        setTitle("Ostrzeżenie!");
        setHeaderText(title);
        setContentText(text);
        setResultConverter(button -> button == confirmButton ? ButtonType.YES : ButtonType.NO);

    }

}
