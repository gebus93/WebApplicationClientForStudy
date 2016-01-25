package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.pricelist.control.*;

import static pl.gebickionline.ui.controller.pricelist.MovementDirection.*;

/**
 * Created by Łukasz on 2016-01-25.
 */
public class GroupManagementMenuVBox extends VBox {
    public GroupManagementMenuVBox(ManageableGroup group) {
        SideBarHeaderLabel headerLabel = new SideBarHeaderLabel(String.format("Zarządzanie grupą\n\"%s\"", group.groupName()));
        headerLabel.getStyleClass().add("margin-top-medium");
        add(headerLabel);
        add(new LabelButton("Edytuj grupę"));
        add(new LabelButton("Usuń grupę"));
        LabelButton moveUp = new LabelButton("Przenieś o 1 do góry");
        moveUp.setOnMouseClicked(event -> moveUp(group));
        add(moveUp);
        LabelButton moveDown = new LabelButton("Przenieś o 1 w dół");
        moveDown.setOnMouseClicked(event -> moveDown(group));
        add(moveDown);

    }

    private void moveDown(ManageableGroup group) {
        PriceList.getInstance().moveGroup(group, DOWN);
    }

    private void moveUp(ManageableGroup group) {
        PriceList.getInstance().moveGroup(group, UP);
    }

    private void add(Node node) {
        getChildren().add(node);
    }
}
