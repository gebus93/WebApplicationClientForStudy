package pl.gebickionline.ui.controller.pricelist;

import javafx.beans.property.StringProperty;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pl.gebickionline.pricelist.ManageableService;

import static java.util.stream.Collectors.toList;

/**
 * Created by Łukasz on 2016-01-18.
 */
public class ServiceDialog extends Dialog<ManageableService> {
    private GridPane layout = new GridPane();
    private ButtonType saveButtonType = new ButtonType("Zapisz", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
    private TextField serviceNameField = new TextField();
    private CheckBox visible = new CheckBox();
    private ChoiceBox<AvailableGroup> group = new ChoiceBox<>();
    private ChoiceBox<PriceType> priceType = new ChoiceBox<>();
    private TextField concretePriceField = createPriceField();
    private TextField minPriceField = createPriceField();
    private TextField maxPriceField = createPriceField();
    private HBox priceContainer = new HBox();

    public ServiceDialog() {
        this(new ManageableService.Builder().build());
    }

    private enum PriceType {
        CONCRETE("Stała cena"),
        RANGE("Przedział cen");


        private final String value;

        PriceType(String value) {

            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public ServiceDialog(ManageableService service) {
        super();

        initializeGroupChoiceBox(service.groupId());
        initializePriceTypeChoiceBox(service.price() != null);

        serviceNameField.setText(service.serviceName());
        visible.setSelected(service.isVisible());
        concretePriceField.setText(getStringPrice(service.price()));
        minPriceField.setText(getStringPrice(service.minPrice()));
        maxPriceField.setText(getStringPrice(service.maxPrice()));

        getDialogPane().setContent(layout);
        getDialogPane().getButtonTypes().addAll(saveButtonType, cancel);

        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 150, 10, 10));

        layout.add(createLabel("Nazwa usługi"), 0, 0);
        layout.add(serviceNameField, 1, 0);

        layout.add(createLabel("Widoczność dla klientów"), 0, 1);
        layout.add(visible, 1, 1);

        layout.add(createLabel("Grupa"), 0, 2);
        layout.add(this.group, 1, 2);

        layout.add(createLabel("Typ ceny"), 0, 3);
        layout.add(this.priceType, 1, 3);

        layout.add(createLabel("Cena"), 0, 4);
        layout.add(this.priceContainer, 1, 4);

        Node saveButton = getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(service.serviceName() == null || service.serviceName().isEmpty());

        serviceNameField.setOnKeyReleased(event -> {
            saveButton.setDisable(serviceNameField.getText().isEmpty());
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {


                return new ManageableService.Builder()
                        .withId(service.id())
                        .withGroupId(this.group.getSelectionModel().getSelectedItem().id)
                        .withServiceName(serviceNameField.getText())
                        .withVisible(visible.isSelected())
                        .withPrice(getPrice(concretePriceField))
                        .withMinPrice(getPrice(minPriceField))
                        .withMaxPrice(getPrice(maxPriceField))
                        .build();
            }

            return null;
        });

    }

    private String getStringPrice(Integer price) {
        return price == null ? null : String.format("%.2f", price / 100f);
    }

    private Integer getPrice(TextField textField) {
        String text = textField.getText();
        if (text == null || text.isEmpty())
            return null;
        return (int) (Double.valueOf(text) * 100);
    }

    private void initializePriceTypeChoiceBox(boolean concretePriceSelected) {
        priceType.getItems().addAll(PriceType.CONCRETE, PriceType.RANGE);
        priceType
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener((obs, oldType, newType) -> showPriceFields(priceTypeOf(newType)));


        priceType.getSelectionModel().select(concretePriceSelected ? 0 : 1);

    }

    private PriceType priceTypeOf(Number indexOfPriceType) {
        return PriceType.values()[indexOfPriceType.intValue()];
    }

    private void showPriceFields(PriceType priceType) {
        concretePriceField.setText("");
        minPriceField.setText("");
        maxPriceField.setText("");
        priceContainer.getChildren().clear();

        if (priceType == PriceType.CONCRETE)
            priceContainer.getChildren()
                    .addAll(
                            this.concretePriceField,
                            createLabel(" zł")
                    );
        else
            priceContainer.getChildren()
                    .addAll(
                            createLabel("od "),
                            this.minPriceField,
                            createLabel(" do "),
                            this.maxPriceField,
                            createLabel(" zł")
                    );
    }

    private void initializeGroupChoiceBox(Long groupId) {
        group.getItems().addAll(
                PriceList.getInstance()
                        .getGroupList()
                        .stream()
                        .sorted()
                        .map(g -> new AvailableGroup(g.id(), g.groupName()))
                        .collect(toList())
        );

        int selectedGroup = 0;

        if (groupId != null)
            for (int i = 0; i < group.getItems().size(); i++)
                if (groupId == group.getItems().get(i).id) {
                    selectedGroup = i;
                    break;
                }

        group.getSelectionModel().select(selectedGroup);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setMaxHeight(Double.MAX_VALUE);
        return label;
    }


    private TextField createPriceField() {
        TextField textField = new TextField("0");
        int width = 85;
        textField.setMinWidth(width);
        textField.setMaxWidth(width);
        textField.setPrefWidth(width);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                return;

            if (!newValue.matches("[0-9]+[\\.,]?[0-9]{0,2}")) {
                ((StringProperty) observable).setValue(oldValue);
                return;
            }

            if (newValue.matches("([0-9]*),([0-9]{0,2})")) {
                ((StringProperty) observable).setValue(newValue.replaceAll("([0-9]*),([0-9]{0,2})", "$1.$2"));
                return;
            }

            try {
                Double aDouble = Double.parseDouble(newValue);
                int tmp = aDouble.intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ((StringProperty) observable).setValue(oldValue);
            }
        });

        return textField;
    }

    private class AvailableGroup {
        private long id;
        private String name;

        public AvailableGroup(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
