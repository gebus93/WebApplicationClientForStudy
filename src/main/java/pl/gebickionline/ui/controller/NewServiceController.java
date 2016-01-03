package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-03.
 */
public class NewServiceController {
    private Main mainApp;

    @FXML
    private Label groupNameLabel;
    @FXML
    private TextField serviceNameField;

    @FXML
    private ChoiceBox<String> visibilityField;

    @FXML
    private ChoiceBox<String> priceTypeField;

    @FXML
    private TextField concretePriceField;
    @FXML
    private Label minPriceLabel;

    @FXML
    private TextField minPriceField;

    @FXML
    private Label maxPriceLabel;

    @FXML
    private TextField maxPriceField;

    private enum PriceType {CONCRETE, RANGE}

    @FXML
    private void initialize() {
        priceTypeField
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener((obs, oldType, newType) -> showPriceFields(priceTypeOf(newType)));
    }

    private PriceType priceTypeOf(Number indexOfPriceType) {
        return PriceType.values()[indexOfPriceType.intValue()];
    }

    private void showPriceFields(PriceType priceType) {
        concretePriceField.setText("");
        minPriceField.setText("");
        maxPriceField.setText("");

        if (priceType == PriceType.CONCRETE) {
            hideField(this.minPriceField);
            hideField(this.maxPriceField);
            hideField(this.minPriceLabel);
            hideField(this.maxPriceLabel);
            showPriceField(this.concretePriceField);
        } else {
            hideField(this.concretePriceField);
            showPriceField(this.minPriceField);
            showPriceField(this.maxPriceField);
            showPriceRangeLabel(this.minPriceLabel);
            showPriceRangeLabel(this.maxPriceLabel);
        }
    }

    private void showPriceRangeLabel(Label label) {
        label.setVisible(true);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        label.setMaxWidth(Region.USE_COMPUTED_SIZE);
        label.setMinWidth(Region.USE_COMPUTED_SIZE);
    }

    private void showPriceField(TextField field) {
        field.setVisible(true);
        field.setPrefWidth(100);
        field.setMaxWidth(Region.USE_COMPUTED_SIZE);
        field.setMinWidth(Region.USE_COMPUTED_SIZE);

    }

    private void hideField(Control field) {
        field.setVisible(false);
        field.setPrefWidth(0);
        field.setMaxWidth(0);
        field.setMinWidth(0);
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Błąd podczas dodawania usługi");
        alert.setHeaderText("Formularz został nieprawidłowo wypełniony");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
