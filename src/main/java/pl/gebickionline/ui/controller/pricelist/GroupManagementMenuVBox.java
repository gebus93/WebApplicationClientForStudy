package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.ConfirmationAlert;
import pl.gebickionline.ui.controller.pricelist.control.*;

import java.util.Optional;

import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-25.
 */
public class GroupManagementMenuVBox extends VBox {

    private final LabelButton editButton = new LabelButton("Edytuj grupę");
    private final LabelButton deleteButton = new LabelButton("Usuń grupę");
    private final LabelButton moveUp = new LabelButton("Przenieś o 1 do góry");
    private final LabelButton moveDown = new LabelButton("Przenieś o 1 w dół");
    private final PriceList priceList = PriceList.getInstance();
    private final PriceListManagerVBox priceListManagerVBox;

    public GroupManagementMenuVBox(PriceListManagerVBox priceListManagerVBox, ManageableGroup group) {
        this.priceListManagerVBox = priceListManagerVBox;
        SideBarHeaderLabel headerLabel = new SideBarHeaderLabel(String.format("Zarządzanie grupą\n\"%s\"", group.groupName()));
        headerLabel.getStyleClass().add("margin-top-medium");

        moveUp.setOnMouseClicked(event -> priceList.moveGroup(group, UP));
        moveDown.setOnMouseClicked(event -> priceList.moveGroup(group, DOWN));
        deleteButton.setOnMouseClicked(event -> removeGroup(group));
        editButton.setOnMouseClicked(event -> editGroup(group));

        getChildren()
                .addAll(
                        headerLabel,
                        editButton,
                        deleteButton,
                        moveUp,
                        moveDown
                );

    }

    private void editGroup(ManageableGroup group) {
        ServiceGroupDialog serviceGroupDialog = new ServiceGroupDialog(group);
        serviceGroupDialog.setTitle(String.format("Edycja grupy \"%s\"", group.groupName()));
        Optional<ManageableGroup> manageableGroupOptional = serviceGroupDialog.showAndWait();

        if (manageableGroupOptional.isPresent()) {

            PriceList priceList = PriceList.getInstance();
            priceList.updateGroup(manageableGroupOptional.get());
            PriceListManager.getInstance().updateManageablePriceList(priceList.asManageablePriceList());
        }

    }

    private void removeGroup(ManageableGroup group) {
        ConfirmationAlert confirmationAlert = new ConfirmationAlert(
                "Potwierdzenie usunięcia usługi",
                String.format("Czy na pewno chcesz usunąć grupę \"%s\" wraz ze wszystkimi jej usługami?", group.groupName())
        );
        if (confirmationAlert.showAndWait().get() == ButtonType.YES) {
            priceList.removeGroup(group);
            priceListManagerVBox.hideManagementMenu();
        }
    }

}
