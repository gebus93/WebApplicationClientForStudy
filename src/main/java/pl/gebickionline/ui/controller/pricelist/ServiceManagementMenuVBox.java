package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import pl.gebickionline.pricelist.ManageableService;
import pl.gebickionline.ui.controller.pricelist.control.*;

import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class ServiceManagementMenuVBox extends VBox {
    public ServiceManagementMenuVBox(ManageableService service) {
        SideBarHeaderLabel headerLabel = new SideBarHeaderLabel(String.format("Zarządzanie usługą\n\"%s\"", service.serviceName()));
        headerLabel.getStyleClass().add("margin-top-medium");
        add(headerLabel);
        add(new LabelButton("Edytuj usługę"));
        add(new LabelButton("Usuń usługę"));
        LabelButton moveUp = new LabelButton("Przenieś o 1 do góry");
        moveUp.setOnMouseClicked(event -> moveUp(service));
        add(moveUp);
        LabelButton moveDown = new LabelButton("Przenieś o 1 w dół");
        moveDown.setOnMouseClicked(event -> moveDown(service));
        add(moveDown);

    }

    private void moveDown(ManageableService service) {
        PriceList.getInstance().moveService(service, DOWN);
    }

    private void moveUp(ManageableService service) {
        PriceList.getInstance().moveService(service, UP);
    }

    private void add(Node node) {
        getChildren().add(node);
    }
}
