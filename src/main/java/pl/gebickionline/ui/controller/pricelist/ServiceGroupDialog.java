package pl.gebickionline.ui.controller.pricelist;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pl.gebickionline.pricelist.ManageableGroup;

import static java.util.stream.Collectors.toList;

/**
 * Created by Łukasz on 2016-01-18.
 */
public class ServiceGroupDialog extends Dialog<ManageableGroup> {
    private GridPane layout = new GridPane();
    private ButtonType saveButtonType = new ButtonType("Zapisz", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
    private TextField groupNameField = new TextField();
    private CheckBox visible = new CheckBox();
    private ChoiceBox<GroupOrder> location = new ChoiceBox<>();

    public ServiceGroupDialog(ManageableGroup group) {
        super();

        initializeLocationChoiceBox(group.ordinal());

        getDialogPane().setContent(layout);
        getDialogPane().getButtonTypes().addAll(saveButtonType, cancel);

        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 150, 10, 10));

        layout.add(createLabel("Nazwa grupy"), 0, 0);
        layout.add(groupNameField, 1, 0);

        layout.add(createLabel("Widoczność dla klientów"), 0, 1);
        layout.add(visible, 1, 1);

        layout.add(createLabel("Położenie"), 0, 2);
        layout.add(location, 1, 2);

        Node saveButton = getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        groupNameField.setOnKeyReleased(event -> {
            saveButton.setDisable(groupNameField.getText().isEmpty());
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new ManageableGroup(
                        group.id(),
                        groupNameField.getText(),
                        visible.isSelected(),
                        location.getSelectionModel().getSelectedItem().order);
            }

            return null;
        });

    }

    private void initializeLocationChoiceBox(long selectedIndex) {
        location.getItems().add(new GroupOrder(0, "Na początku"));
        location.getItems().addAll(
                PriceList.getInstance()
                        .getGroupList()
                        .stream()
                        .sorted()
                        .map(g -> new GroupOrder(g.ordinal() + 1, "Po grupie \"" + g.groupName() + "\""))
                        .collect(toList())
        );
        location.getSelectionModel().select((int) selectedIndex);
    }

    public ServiceGroupDialog() {
        this(new ManageableGroup());
    }

    private Label createLabel(String text) {
        return new Label(text);
    }

    private class GroupOrder {
        private long order;
        private String label;

        public GroupOrder(long order, String label) {
            this.order = order;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

}
