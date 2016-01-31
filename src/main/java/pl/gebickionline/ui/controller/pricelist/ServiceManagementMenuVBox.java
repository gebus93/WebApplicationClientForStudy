package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.ConfirmationAlert;
import pl.gebickionline.ui.controller.pricelist.control.*;

import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class ServiceManagementMenuVBox extends VBox {

    private final LabelButton removeButton = new LabelButton("Usuń usługę");
    private final LabelButton moveUp = new LabelButton("Przenieś o 1 do góry");
    private final LabelButton moveDown = new LabelButton("Przenieś o 1 w dół");
    private final LabelButton editButon = new LabelButton("Edytuj usługę");

    public ServiceManagementMenuVBox(ManageableService service) {
        moveUp.setOnMouseClicked(event -> PriceList.getInstance().moveService(service, UP));
        moveDown.setOnMouseClicked(event -> PriceList.getInstance().moveService(service, DOWN));
        removeButton.setOnMouseClicked(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert(
                    "Potwierdzenie usunięcia usługi",
                    String.format("Czy na pewno chcesz usunąć usługę \"%s\"?", service.serviceName())
            );
            if (confirmationAlert.showAndWait().get() == ButtonType.YES) {
                PriceList.getInstance().removeService(service);
            }
        });

        SideBarHeaderLabel headerLabel = new SideBarHeaderLabel(String.format("Zarządzanie usługą\n\"%s\"", service.serviceName()));
        headerLabel.getStyleClass().add("margin-top-medium");
        getChildren().addAll(headerLabel, editButon, removeButton, moveUp, moveDown);

    }

}
