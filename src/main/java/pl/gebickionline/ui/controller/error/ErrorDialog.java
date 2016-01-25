package pl.gebickionline.ui.controller.error;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * Created by Łukasz on 2016-01-25.
 */
public class ErrorDialog extends Alert {
    private GridPane layout = new GridPane();

    public ErrorDialog(Throwable ex) {
        super(AlertType.ERROR);
        getDialogPane().setContent(layout);
        setResizable(true);
        setTitle("Błąd krytyczny");
        setHeaderText("Wystąpił nieoczekiwany błąd!");

        TextArea message = getTextArea(getStackTrace(ex));
        GridPane.setVgrow(message, Priority.ALWAYS);
        GridPane.setHgrow(message, Priority.ALWAYS);

        layout.setMaxWidth(Double.MAX_VALUE);
        layout.add(new Label("Poniżej znajduje się StackTrace wyjątku:"), 0, 0);
        layout.add(message, 0, 1);

    }

    @NotNull
    private TextArea getTextArea(String stackTrace) {
        TextArea message = new TextArea(stackTrace);
        message.setEditable(false);
        message.setWrapText(true);
        message.setMaxWidth(Double.MAX_VALUE);
        message.setMaxHeight(Double.MAX_VALUE);
        return message;
    }

    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


}
