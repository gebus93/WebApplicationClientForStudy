package pl.gebickionline.ui.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import pl.gebickionline.contact.ContactDataManager;

public class ContactDataEditorController {
    @FXML
    public BorderPane rootLayout;

    @FXML
    public HTMLEditor editorField;
    private String lastSavedPageContent;
    private boolean textChanged;

    @FXML
    private void initialize() {
        setActualPageContentToEditor();
    }

    @FXML
    private void saveChanges() {

        ContactDataManager contactDataManager = ContactDataManager.getInstance();
        String htmlText = getBodyFromEditor();
        if (htmlText == null || htmlText.trim().isEmpty())
            showError("Treść podstrony nie może być pusta");

        contactDataManager.updateContentOfContactDataPage(htmlText);
        setActualPageContentToEditor();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd krytyczny!");
        alert.setHeaderText("Nieprawidłowe dane strony kontakowej");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void resetChanges() {
        if (!textChanged)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz porzucić dokonane zmiany?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
            setActualPageContentToEditor();
    }

    private void setActualPageContentToEditor() {
        ContactDataManager contactDataManager = ContactDataManager.getInstance();
        lastSavedPageContent = contactDataManager.getContentOfContactDataPage();
        Platform.runLater(() -> editorField.setHtmlText(lastSavedPageContent));
        textChanged = false;
    }

    public void textChanged(Event event) {
        textChanged = !getBodyFromEditor().equals(lastSavedPageContent);
    }

    private String getBodyFromEditor() {
        return editorField.getHtmlText().replaceAll(".*<body[^>]*>(.*?)</body>.*", "$1");
    }
}
