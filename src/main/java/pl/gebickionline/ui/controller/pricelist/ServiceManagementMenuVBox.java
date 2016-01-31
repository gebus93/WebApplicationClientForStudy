package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.ConfirmationAlert;
import pl.gebickionline.ui.controller.pricelist.control.*;

import java.util.Optional;

import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class ServiceManagementMenuVBox extends VBox {

    private final LabelButton removeButton = new LabelButton("Usuń usługę");
    private final LabelButton moveUp = new LabelButton("Przenieś o 1 do góry");
    private final LabelButton moveDown = new LabelButton("Przenieś o 1 w dół");
    private final LabelButton editButon = new LabelButton("Edytuj usługę");
    private final PriceList priceList = PriceList.getInstance();
    private final PriceListManagerVBox priceListManagerVBox;

    public ServiceManagementMenuVBox(PriceListManagerVBox priceListManagerVBox, ManageableService service) {
        this.priceListManagerVBox = priceListManagerVBox;
        moveUp.setOnMouseClicked(event -> priceList.moveService(service, UP));
        moveDown.setOnMouseClicked(event -> priceList.moveService(service, DOWN));
        removeButton.setOnMouseClicked(event -> removeService(service));
        editButon.setOnMouseClicked(event -> editService(service));

        SideBarHeaderLabel headerLabel = new SideBarHeaderLabel(String.format("Zarządzanie usługą\n\"%s\"", service.serviceName()));
        headerLabel.getStyleClass().add("margin-top-medium");
        getChildren().addAll(headerLabel, editButon, removeButton, moveUp, moveDown);

    }

    private void editService(ManageableService service) {
        ServiceDialog serviceGroupDialog = new ServiceDialog(service);
        serviceGroupDialog.setTitle(String.format("Edycja usługi \"%s\"", service.serviceName()));
        Optional<ManageableService> manageableService = serviceGroupDialog.showAndWait();

        if (manageableService.isPresent()) {

            PriceList priceList = PriceList.getInstance();
            priceList.updateService(service, manageableService.get());
            PriceListManager.getInstance().updateManageablePriceList(priceList.asManageablePriceList());

        }

    }

    private void removeService(ManageableService service) {
        ConfirmationAlert confirmationAlert = new ConfirmationAlert(
                "Potwierdzenie usunięcia usługi",
                String.format("Czy na pewno chcesz usunąć usługę \"%s\"?", service.serviceName())
        );
        if (confirmationAlert.showAndWait().get() == ButtonType.YES) {
            priceList.removeService(service);
            priceListManagerVBox.hideManagementMenu();
        }
    }

}
