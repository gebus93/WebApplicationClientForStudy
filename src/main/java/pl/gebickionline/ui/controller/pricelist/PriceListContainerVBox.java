package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pl.gebickionline.pricelist.ManageableService;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListContainerVBox extends VBox {
    private static final String ITEM_SELECTED_CLASS = "pricelist-item-selected";
    private Node selectedNode;

    public PriceListContainerVBox() {
        super();

        getStyleClass().addAll("pricelist-wrapper");
    }

    public Label addGroup(String groupName) {
        Label groupNameLabel = new Label(groupName);
        groupNameLabel.getStyleClass().add("pricelist-group-name");
        getChildren().add(groupNameLabel);
        return groupNameLabel;
    }

    public HBox addService(ManageableService service) {
        HBox serviceBox = new HBox();
        Label serviceName = new Label(service.serviceName());
        serviceName.getStyleClass().add("pricelist-service-name");
        Label price = new Label();
        price.getStyleClass().add("pricelist-service-price");

        if (service.price() == null)
            price.setText(String.format("od %.2f do %.2f zł", service.minPrice() / 100.0f, service.maxPrice() / 100.0f));
        else
            price.setText(String.format("%.2f zł", service.price() / 100.0f));

        serviceBox.getChildren().addAll(serviceName, price);
        getChildren().add(serviceBox);

        return serviceBox;

    }

    public void removeSelectionFromAllElements() {
        getChildren()
                .forEach(n -> n.getStyleClass().remove(ITEM_SELECTED_CLASS));
    }

    public void changeSelectedItem(Node node) {
        removeSelectionFromAllElements();
        if (selectedNode != node) {
            selectedNode = node;
            selectedNode.getStyleClass().add(ITEM_SELECTED_CLASS);
        } else
            selectedNode = null;
    }

    public ScrollPane getWrappedByScrollPane() {
        ScrollPane priceListScrollPane = new ScrollPane();
        priceListScrollPane.setContent(this);
        priceListScrollPane.setFitToHeight(true);
        priceListScrollPane.setFitToWidth(true);
        return priceListScrollPane;
    }

    public void clear() {
        getChildren().clear();
    }
}
